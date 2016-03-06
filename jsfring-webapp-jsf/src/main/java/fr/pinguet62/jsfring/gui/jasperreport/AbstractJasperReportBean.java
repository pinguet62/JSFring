package fr.pinguet62.jsfring.gui.jasperreport;

import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.CSV;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.DOCX;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.GRAPHICS_2D;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.HTML;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.ODS;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.ODT;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.PDF;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.PPTX;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.RTF;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.TEXT;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.XLS;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.XLSX;
import static fr.pinguet62.jsfring.gui.jasperreport.ExportType.XML;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

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
     * @see #getStreamedContent(ExportType)
     * @see ExportType#CSV
     */
    public StreamedContent getCsvFile() throws JRException, SQLException {
        return getStreamedContent(CSV);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#DOCX
     */
    public StreamedContent getDocxFile() throws JRException, SQLException {
        return getStreamedContent(DOCX);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#GRAPHICS_2D
     */
    public StreamedContent getGraphics2dFile() throws JRException, SQLException {
        return getStreamedContent(GRAPHICS_2D);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#HTML
     */
    public StreamedContent getHtmlFile() throws JRException, SQLException {
        return getStreamedContent(HTML);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#ODS
     */
    public StreamedContent getOdsFile() throws JRException, SQLException {
        return getStreamedContent(ODS);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#ODT
     */
    public StreamedContent getOdtFile() throws JRException, SQLException {
        return getStreamedContent(ODT);
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
     * @see #getStreamedContent(ExportType)
     * @see ExportType#PDF
     */
    public StreamedContent getPdfFile() throws JRException, SQLException {
        return getStreamedContent(PDF);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#PPTX
     */
    public StreamedContent getPptxFile() throws JRException, SQLException {
        return getStreamedContent(PPTX);
    }

    /**
     * Get the resource path of report to use.<br>
     * Must contain the extension ({@code jrxml}, {@code .jasper} or other).
     *
     * @return The path.
     */
    protected abstract String getReportPath();

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#RTF
     */
    public StreamedContent getRtfFile() throws JRException, SQLException {
        return getStreamedContent(RTF);
    }

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
    protected StreamedContent getStreamedContent(ExportType targetType) throws JRException, SQLException {
        // Jasper: compile
        JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream(getReportPath()));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, getParameters(), dataSource.getConnection());

        // Jasper: export
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRAbstractExporter<?, ?, ? super GeneralExporterOutput, ?> exporter = targetType.getExporterFactory().get();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new GeneralExporterOutput(outputStream));
        exporter.exportReport();
        byte[] bytes = outputStream.toByteArray();

        // PrimeFaces download
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        return new DefaultStreamedContent(inputStream, targetType.getMime(), "report." + targetType.getExtension());
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#TEXT
     */
    public StreamedContent getTextFile() throws JRException, SQLException {
        return getStreamedContent(TEXT);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#XLSX
     */
    public StreamedContent getXlsFile() throws JRException, SQLException {
        return getStreamedContent(XLS);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#XLSX
     */
    public StreamedContent getXlsxFile() throws JRException, SQLException {
        return getStreamedContent(XLSX);
    }

    /**
     * @see #getStreamedContent(ExportType)
     * @see ExportType#XML
     */
    public StreamedContent getXmlFile() throws JRException, SQLException {
        return getStreamedContent(XML);
    }

}