package fr.pinguet62.jsfring.gui.jasperreport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.export.SimpleExporterInput;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/** Abstract bean used to display and export {@link JasperReport}. */
public abstract class AbstractJasperReportBean implements Serializable {

    private static final long serialVersionUID = 1;

    @Inject
    private transient DataSource dataSource;

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

    /**
     * Export the current report to {@link ExportType target format}.
     * <p>
     * <ol>
     * <li>Compile and initialize the {@link JasperReport}</li>
     * <li>{@link JRAbstractExporter Export} the {@link JasperReport}</li>
     * <li>Generate {@link StreamedContent} for PrimeFaces file download</li>
     * </ol>
     *
     * @param targetType The {@link ExportType}.
     * @return The {@link StreamedContent} used by PrimeFaces.
     * @throws JRException Error during compilation or initialization of
     *             {@link JasperReport}
     * @throws SQLException Error with {@link DataSource} injection.
     */
    protected StreamedContent getStreamedContent(ExportType targetType)
            throws JRException, SQLException {
        // Jasper: compile
        JasperReport jasperReport = JasperCompileManager
                .compileReport(getClass().getResourceAsStream(getReportPath()));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                getParameters(), dataSource.getConnection());

        // Jasper: export
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRAbstractExporter<?, ?, ? super GeneralExporterOutput, ?> exporter = targetType
                .getExporterFactory().get();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new GeneralExporterOutput(outputStream));
        exporter.exportReport();
        byte[] bytes = outputStream.toByteArray();

        // PrimeFaces download
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        return new DefaultStreamedContent(inputStream, targetType.getMime(),
                "report.jpg");
    }

}