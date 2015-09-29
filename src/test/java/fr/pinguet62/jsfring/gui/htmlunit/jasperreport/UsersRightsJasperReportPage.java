package fr.pinguet62.jsfring.gui.htmlunit.jasperreport;

import java.io.InputStream;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.jasperreport.sample.UsersRightsJasperReportBean;

/** @see UsersRightsJasperReportBean */
public class UsersRightsJasperReportPage extends AbstractJasperReportPage {

    public UsersRightsJasperReportPage(HtmlPage page) {
        super(page);
    }

    public InputStream exportCSV() {
        return export("CSV");
    }

    public InputStream exportDOCX() {
        return export("docx");
    }

    public InputStream exportODT() {
        return export("odt");
    }

    public InputStream exportPDF() {
        return export("PDF");
    }

    public InputStream exportPPTX() {
        return export("pptx");
    }

    public InputStream exportXLSX() {
        return export("xlsx");
    }

}