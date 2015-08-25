package fr.pinguet62.jsfring.gui.jasperreport;

import java.util.function.Supplier;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
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

/** The available {@link JRAbstractExporter} type. */
public enum ExportType {

    /** CSV */
    CSV("csv", JRCsvExporter::new),
    /**
     * Word processing<br>
     * <i>Office Open XML</i>
     */
    DOCX("docx", JRDocxExporter::new),
    /** Graphics2D */
    GRAPHICS_2D("???", () -> {
        try {
            return new JRGraphics2DExporter();
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }),
    /** HTML/xHTML */
    HTML("html", HtmlExporter::new),
    /**
     * Spreadsheets<br>
     * <i>OpenDocument</i>
     */
    ODS("ods", JROdsExporter::new),
    /**
     * Word processing<br>
     * <i>OpenDocument</i>
     */
    ODT("odt", JROdtExporter::new),
    /** PDF */
    PDF("pdf", JRPdfExporter::new),
    /**
     * Presentation<br>
     * <i>Office Open XML</i>
     */
    PPTX("pptx", JRPptxExporter::new),
    /** RTF */
    RTF("rtf", JRRtfExporter::new),
    /** Test */
    TEXT("txt", JRTextExporter::new),
    /**
     * Spreadsheets<br>
     * <i>Microsoft Excel</i>
     *
     * @see #XLSX {@link #XLSX}: new version (since 2007)
     */
    XLS("xls", JRXlsExporter::new),
    /**
     * Spreadsheets<br>
     * <i>Office Open XML</i>
     */
    XLSX("xlsx", JRXlsxExporter::new),
    /** XML */
    XML("xml", JRXmlExporter::new);

    /** Factory used to get the {@link JRAbstractExporter} implementation. */
    private final Supplier<JRAbstractExporter<?, ?, ? super GeneralExporterOutput, ?>> exporterFactory;

    /** Extension of file type. */
    private final String extension;

    /**
     * @param extension The {@link #extension}.
     * @param exporterFactory The {@link #exporterFactory}.
     */
    private ExportType(
            String extension,
            Supplier<JRAbstractExporter<?, ?, ? super GeneralExporterOutput, ?>> exporterFactory) {
        this.extension = extension;
        this.exporterFactory = exporterFactory;
    }

    /**
     * Get the factory used to get the {@link JRAbstractExporter}
     * implementation.
     *
     * @return The {@link #exporterFactory}.
     */
    public Supplier<JRAbstractExporter<?, ?, ? super GeneralExporterOutput, ?>> getExporterFactory() {
        return exporterFactory;
    }

    /**
     * Get the extension of file type.
     *
     * @return The {@link #extension}.
     */
    public String getExtension() {
        return extension;
    }

}