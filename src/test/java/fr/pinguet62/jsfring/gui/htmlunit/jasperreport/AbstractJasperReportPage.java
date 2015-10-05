package fr.pinguet62.jsfring.gui.htmlunit.jasperreport;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.primefaces.component.panel.Panel;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;
import fr.pinguet62.jsfring.gui.jasperreport.AbstractJasperReportBean;
import fr.pinguet62.jsfring.gui.jasperreport.ExportType;

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