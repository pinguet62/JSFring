package fr.pinguet62.jsfring.gui.htmlunit.jasperreport;

import java.io.InputStream;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.jasperreport.ExportType;
import fr.pinguet62.jsfring.gui.jasperreport.sample.UsersRightsJasperReportBean;

/** @see UsersRightsJasperReportBean */
public class UsersRightsJasperReportPage extends AbstractJasperReportPage {

    public UsersRightsJasperReportPage(HtmlPage page) {
        super(page);
    }

    public InputStream exportCSV() {
        return export(ExportType.CSV.toString());
    }

    public InputStream exportDOCX() {
        return export(ExportType.DOCX.toString());
    }

    public InputStream exportGraphics2D() {
        return export("Graph 2D");
    }

    public InputStream exportHTML() {
        return export(ExportType.HTML.toString());
    }

    public InputStream exportODS() {
        return export(ExportType.ODS.toString());
    }

    public InputStream exportODT() {
        return export(ExportType.ODT.toString());
    }

    public InputStream exportPDF() {
        return export(ExportType.PDF.toString());
    }

    public InputStream exportPPTX() {
        return export(ExportType.PPTX.toString());
    }

    public InputStream exportRTF() {
        return export(ExportType.RTF.toString());
    }

    public InputStream exportTEXT() {
        return export(ExportType.TEXT.toString());
    }

    public InputStream exportXLS() {
        return export(ExportType.XLS.toString());
    }

    public InputStream exportXLSX() {
        return export(ExportType.XLSX.toString());
    }

    public InputStream exportXML() {
        return export(ExportType.XML.toString());
    }

}