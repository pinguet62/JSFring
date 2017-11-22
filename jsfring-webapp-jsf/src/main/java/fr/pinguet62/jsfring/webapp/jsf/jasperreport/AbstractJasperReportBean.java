package fr.pinguet62.jsfring.webapp.jsf.jasperreport;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.export.SimpleExporterInput;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import static fr.pinguet62.jsfring.webapp.jsf.jasperreport.ExportType.*;

/**
 * Abstract bean used to display and export {@link JasperReport}.
 */
public abstract class AbstractJasperReportBean implements Serializable {

    private static final long serialVersionUID = 1;

    @Autowired
    private transient DataSource dataSource;

    /**
     * Get the {@link Map} who contains the parameters for report.
     * <p>
     * By default no parameter is required. But if the report contains parameters, this method must be {@link Override override}
     * .
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
     * Export the current report to {@link ExportType target format}.<br>
     * <ol>
     * <li>Compile and initialize the {@link JasperReport}</li>
     * <li>{@link JRAbstractExporter Export} the {@link JasperReport}</li>
     * <li>Generate {@link StreamedContent} for PrimeFaces file download</li>
     * </ol>
     *
     * @param targetType The {@link ExportType}.
     * @return The {@link StreamedContent} used by PrimeFaces.
     * @throws JasperReportRuntimeException Error rendering report.
     */
    protected StreamedContent getStreamedContent(ExportType targetType) {
        try (Connection connection = dataSource.getConnection()) {
            // Jasper: compile
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream(getReportPath()));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, getParameters(), connection);

            // Jasper: export
            ByteArrayOutputStream outputStream = targetType.export(new SimpleExporterInput(jasperPrint));
            byte[] bytes = outputStream.toByteArray();

            // PrimeFaces download
            InputStream inputStream = new ByteArrayInputStream(bytes);
            return new DefaultStreamedContent(inputStream, targetType.getMime(), "report." + targetType.getExtension());
        } catch (JRException | SQLException e) {
            throw new JasperReportRuntimeException(e);
        }
    }

    public StreamedContent getCsvFile() {
        return getStreamedContent(CSV);
    }

    public StreamedContent getDocxFile() {
        return getStreamedContent(DOCX);
    }

    public StreamedContent getHtmlFile() {
        return getStreamedContent(HTML);
    }

    public StreamedContent getJsonFile() {
        return getStreamedContent(JSON);
    }

    public StreamedContent getOdsFile() {
        return getStreamedContent(ODS);
    }

    public StreamedContent getOdtFile() {
        return getStreamedContent(ODT);
    }

    public StreamedContent getPdfFile() {
        return getStreamedContent(PDF);
    }

    public StreamedContent getPptxFile() {
        return getStreamedContent(PPTX);
    }

    public StreamedContent getRtfFile() {
        return getStreamedContent(RTF);
    }

    public StreamedContent getTextFile() {
        return getStreamedContent(TEXT);
    }

    public StreamedContent getXlsFile() {
        return getStreamedContent(XLS);
    }

    public StreamedContent getXlsxFile() {
        return getStreamedContent(XLSX);
    }

    public StreamedContent getXmlFile() {
        return getStreamedContent(XML);
    }

}