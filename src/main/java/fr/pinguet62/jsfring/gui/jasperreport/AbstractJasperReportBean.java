package fr.pinguet62.jsfring.gui.jasperreport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.export.SimpleExporterInput;

/** Abstract bean used to display and export {@link JasperReport}. */
public abstract class AbstractJasperReportBean implements Serializable {

    private static final long serialVersionUID = 1;

    @Inject
    private transient DataSource dataSource;

    /**
     * Export the current report to {@link ExportType target format}.
     * <p>
     * <ol>
     * <li>Compile and initialize the {@link JasperReport}</li>
     * <li>{@link JRAbstractExporter Export} the report</li>
     * <li>Configure the {@link HttpServletResponse} to download generated file</li>
     * </ol>
     *
     * @param targetType The {@link ExportType}.
     * @throws SQLException Error with {@link DataSource} injection.
     * @throws JRException Error during compilation or initialization of report.
     * @throws IOException Error with {@link ServletResponse#getOutputStream()}.
     */
    protected void export(ExportType targetType) throws SQLException,
            JRException, IOException {
        // Jasper: compile
        JasperReport jasperReport = JasperCompileManager
                .compileReport(getClass().getResourceAsStream(getReportPath()));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                getParameters(), dataSource.getConnection());

        // Jasper: export
        ByteArrayOutputStream bytesOs = new ByteArrayOutputStream();
        JRAbstractExporter<?, ?, ? super GeneralExporterOutput, ?> exporter = targetType
                .getExporterFactory().get();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new GeneralExporterOutput(bytesOs));
        exporter.exportReport();
        byte[] bytes = bytesOs.toByteArray();

        FacesContext context = FacesContext.getCurrentInstance();

        // Webapp: file download
        HttpServletResponse response = (HttpServletResponse) context
                .getExternalContext().getResponse();
        response.addHeader("Content-disposition",
                "attachment; filename=report." + targetType.getExtension());
        response.setContentType(targetType.getMime());
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();

        FacesContext.getCurrentInstance().responseComplete();
    }

    /**
     * Get the {@link Map} who contains the parameters for report.
     * <p>
     * By default no parameter is required. But if the report contains
     * parameters, this method must be {@link Override override}.
     *
     * @return The {@link Map} of parameters.
     */
    protected Map<String, Object> getParameters() {
        return null;
    }

    /**
     * Get the resource path of report to use.<br>
     * Must contain the extension ({@code jrxml}, {@code .jasper} or other).
     *
     * @return The path.
     */
    protected abstract String getReportPath();

}