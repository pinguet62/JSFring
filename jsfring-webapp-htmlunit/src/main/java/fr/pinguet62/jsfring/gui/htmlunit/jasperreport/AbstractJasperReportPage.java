package fr.pinguet62.jsfring.gui.htmlunit.jasperreport;

import java.awt.Panel;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;

/** @see AbstractJasperReportBean */
public abstract class AbstractJasperReportPage extends AbstractPage {

    public AbstractJasperReportPage(HtmlPage page) {
        super(page);
    }

    /**
     * Into the {@link Panel} footer, find the {@link HtmlButton} from title.
     *
     * @return The {@link InputStream} returned after export button click.
     */
    protected InputStream export(String buttonTitle) {
        @SuppressWarnings("unchecked")
        List<HtmlButton> buttons = (List<HtmlButton>) page.getByXPath(
                "//div[contains(@class, 'ui-layout-center')]//form//div[contains(@class, 'ui-panel-footer')]//button");
        HtmlButton exporter = buttons.stream()
                .filter(button -> ((HtmlSpan) button.getByXPath("./span").get(0)).getTextContent().equals(buttonTitle))
                .findAny().get();
        try {
            return exporter.click().getWebResponse().getContentAsStream();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    public InputStream exportCSV() {
        return export("csv");
    }

    public InputStream exportDOCX() {
        return export("docx");
    }

    public InputStream exportGraphics2D() {
        return export("Graph 2D");
    }

    public InputStream exportHTML() {
        return export("html");
    }

    public InputStream exportODS() {
        return export("ods");
    }

    public InputStream exportODT() {
        return export("odt");
    }

    public InputStream exportPDF() {
        return export("pdf");
    }

    public InputStream exportPPTX() {
        return export("pptx");
    }

    public InputStream exportRTF() {
        return export("rtf");
    }

    public InputStream exportTEXT() {
        return export("text");
    }

    public InputStream exportXLS() {
        return export("xls");
    }

    public InputStream exportXLSX() {
        return export("xlsx");
    }

    public InputStream exportXML() {
        return export("xml");
    }

}