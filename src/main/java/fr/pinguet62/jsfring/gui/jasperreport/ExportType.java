package fr.pinguet62.jsfring.gui.jasperreport;

import java.util.function.Supplier;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRExporterContext;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.ExporterConfiguration;
import net.sf.jasperreports.export.ReportExportConfiguration;

/** The available {@link JRAbstractExporter} type. */
public enum ExportType {

    /** Comma-separated values */
    CSV(JRCsvExporter::new, "text/csv", "csv"),

    /**
     * Word processing<br>
     * <i>Office Open XML</i>
     */
    DOCX(JRDocxExporter::new, "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx"),

    /** Graphics2D */
    GRAPHICS_2D(() -> {
        try {
            return new JRGraphics2DExporter();
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }, "???", "???"),

    /** Hypertext Markup Language */
    HTML(HtmlExporter::new, "text/html", "html"),

    /**
     * Spreadsheets<br>
     * <i>OpenDocument</i>
     */
    ODS(JROdsExporter::new, "application/vnd.oasis.opendocument.spreadsheet", "ods"),

    /**
     * Word processing<br>
     * <i>OpenDocument</i>
     */
    ODT(JROdtExporter::new, "application/vnd.oasis.opendocument.text", "odt"),

    /** PDF */
    PDF(JRPdfExporter::new, "application/pdf", "pdf"),

    /**
     * Presentation<br>
     * <i>Office Open XML</i>
     */
    PPTX(JRPptxExporter::new, "application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx"),

    /** Rich Text Format */
    RTF(JRRtfExporter::new, "application/rtf", "rtf"),

    /** Plain text */
    TEXT(JRTextExporter::new, "text/plain", "txt"),

    /**
     * Spreadsheets<br>
     * <i>Microsoft Excel</i>
     *
     * @see #XLSX {@link #XLSX}: new version (since 2007)
     */
    XLS(JRXlsExporter::new, "application/vnd.ms-excel", "xls"),

    /**
     * Spreadsheets<br>
     * <i>Office Open XML</i>
     */
    XLSX(JRXlsxExporter::new, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx"),

    /** Extensible Markup Language */
    XML(JRXmlExporter::new, "application/xml", "xml");

    /** Factory used to get the {@link JRAbstractExporter} implementation. */
    private final Supplier<JRAbstractExporter<? extends ReportExportConfiguration, ? extends ExporterConfiguration, ? super GeneralExporterOutput, ? extends JRExporterContext>> exporterFactory;

    /** Extension of file type. */
    private final String extension;

    /** The mime content type. */
    private final String mime;

    /**
     * @param exporterFactory The {@link #exporterFactory}.
     * @param mime The mime content type.
     * @param extension The {@link #extension}.
     */
    private ExportType(
            Supplier<JRAbstractExporter<? extends ReportExportConfiguration, ? extends ExporterConfiguration, ? super GeneralExporterOutput, ? extends JRExporterContext>> exporterFactory,
            String mime, String extension) {
        this.extension = extension;
        this.mime = mime;
        this.exporterFactory = exporterFactory;
    }

    /**
     * Get the factory used to get the {@link JRAbstractExporter}
     * implementation.
     *
     * @return The {@link #exporterFactory}.
     */
    public Supplier<JRAbstractExporter<? extends ReportExportConfiguration, ? extends ExporterConfiguration, ? super GeneralExporterOutput, ? extends JRExporterContext>> getExporterFactory() {
        return exporterFactory;
    }

    /**
     * Get the extension of file type.<br>
     * Doesn't contains the {@code .} character.
     *
     * @return The {@link #extension}.
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Get the mime content type.
     *
     * @return The {@link #mime}.
     */
    public String getMime() {
        return mime;
    }

}