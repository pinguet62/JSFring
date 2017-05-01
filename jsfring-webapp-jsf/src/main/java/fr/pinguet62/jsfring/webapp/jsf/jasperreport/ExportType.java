package fr.pinguet62.jsfring.webapp.jsf.jasperreport;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.JsonExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleJsonExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXmlExporterOutput;

/** The available {@link JRAbstractExporter} type. */
public enum ExportType {

    /**
     * Comma-separated values
     *
     * @see JRCsvExporter
     */
    CSV("text/csv", "csv") {
        @Override
        public ByteArrayOutputStream export(ExporterInput exporterInput) throws JRException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JRCsvExporter exporter = new JRCsvExporter();
            exporter.setExporterInput(exporterInput);
            exporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream));
            exporter.exportReport();
            return outputStream;
        }
    },

    /**
     * Word processing<br>
     * <i>Office Open XML</i>
     *
     * @see JRDocxExporter
     */
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx") {
        @Override
        public ByteArrayOutputStream export(ExporterInput exporterInput) throws JRException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(exporterInput);
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.exportReport();
            return outputStream;
        }
    },

    /**
     * Hypertext Markup Language
     *
     * @see HtmlExporter
     */
    HTML("text/html", "html") {
        @Override
        public ByteArrayOutputStream export(ExporterInput exporterInput) throws JRException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            HtmlExporter exporter = new HtmlExporter();
            exporter.setExporterInput(exporterInput);
            exporter.setExporterOutput(new SimpleHtmlExporterOutput(outputStream));
            exporter.exportReport();
            return outputStream;
        }
    },

    /**
     * Hypertext Markup Language
     *
     * @see JsonExporter
     */
    JSON("application/json", "json") {
        @Override
        public ByteArrayOutputStream export(ExporterInput exporterInput) throws JRException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JsonExporter exporter = new JsonExporter();
            exporter.setExporterInput(exporterInput);
            exporter.setExporterOutput(new SimpleJsonExporterOutput(outputStream));
            exporter.exportReport();
            return outputStream;
        }
    },

    /**
     * Spreadsheets<br>
     * <i>OpenDocument</i>
     *
     * @see JROdsExporter
     */
    ODS("application/vnd.oasis.opendocument.spreadsheet", "ods") {
        @Override
        public ByteArrayOutputStream export(ExporterInput exporterInput) throws JRException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JROdsExporter exporter = new JROdsExporter();
            exporter.setExporterInput(exporterInput);
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.exportReport();
            return outputStream;
        }
    },

    /**
     * Word processing<br>
     * <i>OpenDocument</i>
     *
     * @see JROdtExporter
     */
    ODT("application/vnd.oasis.opendocument.text", "odt") {
        @Override
        public ByteArrayOutputStream export(ExporterInput exporterInput) throws JRException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JROdtExporter exporter = new JROdtExporter();
            exporter.setExporterInput(exporterInput);
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.exportReport();
            return outputStream;
        }
    },

    /**
     * PDF
     *
     * @see JRPdfExporter
     */
    PDF("application/pdf", "pdf") {
        @Override
        public ByteArrayOutputStream export(ExporterInput exporterInput) throws JRException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(exporterInput);
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.exportReport();
            return outputStream;
        }
    },

    /**
     * Presentation<br>
     * <i>Office Open XML</i>
     *
     * @see JRPptxExporter
     */
    PPTX("application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx") {
        @Override
        public ByteArrayOutputStream export(ExporterInput exporterInput) throws JRException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JRPptxExporter exporter = new JRPptxExporter();
            exporter.setExporterInput(exporterInput);
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.exportReport();
            return outputStream;
        }
    },

    /**
     * Rich Text Format
     *
     * @see
     */
    RTF("application/rtf", "rtf") {
        @Override
        public ByteArrayOutputStream export(ExporterInput exporterInput) throws JRException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JRRtfExporter exporter = new JRRtfExporter();
            exporter.setExporterInput(exporterInput);
            exporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream));
            exporter.exportReport();
            return outputStream;
        }
    },

    /**
     * Plain text
     *
     * @see JRTextExporter
     */
    TEXT("text/plain", "txt") {
        @Override
        public ByteArrayOutputStream export(ExporterInput exporterInput) throws JRException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JRTextExporter exporter = new JRTextExporter();
            exporter.setExporterInput(exporterInput);
            exporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream));
            exporter.exportReport();
            return outputStream;
        }
    },

    /**
     * Spreadsheets<br>
     * <i>Microsoft Excel</i>
     *
     * @see #XLSX {@link #XLSX}: new version (since 2007)
     * @see JRXlsExporter
     */
    XLS("application/vnd.ms-excel", "xls") {
        @Override
        public ByteArrayOutputStream export(ExporterInput exporterInput) throws JRException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(exporterInput);
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.exportReport();
            return outputStream;
        }
    },

    /**
     * Spreadsheets<br>
     * <i>Office Open XML</i>
     *
     * @see JRXlsxExporter
     */
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx") {
        @Override
        public ByteArrayOutputStream export(ExporterInput exporterInput) throws JRException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(exporterInput);
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.exportReport();
            return outputStream;
        }
    },

    /**
     * Extensible Markup Language
     *
     * @see JRXmlExporter
     */
    XML("application/xml", "xml") {
        @Override
        public ByteArrayOutputStream export(ExporterInput exporterInput) throws JRException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JRXmlExporter exporter = new JRXmlExporter();
            exporter.setExporterInput(exporterInput);
            exporter.setExporterOutput(new SimpleXmlExporterOutput(outputStream));
            exporter.exportReport();
            return outputStream;
        }
    };

    /** Extension of file type. */
    private final String extension;

    /** The mime content type. */
    private final String mime;

    /**
     * @param mime
     *            The mime content type.
     * @param extension
     *            The {@link #extension}.
     */
    private ExportType(String mime, String extension) {
        this.extension = extension;
        this.mime = mime;
    }

    /**
     * Visitor method who export the report to {@link OutputStreamt}.
     *
     * @param The
     *            {@link ExporterInput} to export.
     * @return The {@link OutputStream}.
     * @throws JRException
     *             When an error occurs during export.
     */
    public abstract ByteArrayOutputStream export(ExporterInput exporterInput) throws JRException;

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