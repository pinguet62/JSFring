package fr.pinguet62.jsfring.gui.htmlunit.jasperreport;

import java.util.Date;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

import fr.pinguet62.jsfring.gui.htmlunit.field.AutocompleteInputText;
import fr.pinguet62.jsfring.gui.htmlunit.field.DatetimeInputText;
import fr.pinguet62.jsfring.gui.htmlunit.field.InputText;
import fr.pinguet62.jsfring.gui.jasperreport.sample.ParametersJasperReportBean;

/** @see ParametersJasperReportBean */
public final class ParametersJasperReportPage extends AbstractJasperReportPage {

    public ParametersJasperReportPage(HtmlPage page) {
        super(page);
    }

    /** @param row The param index. */
    private HtmlTableDataCell getParam(int row) {
        return (HtmlTableDataCell) page
                .getByXPath(
                        "//form//div[contains(@class, 'ui-panel')]/div[contains(@class, 'ui-panel-content')]/table/tbody/tr/td[2]")
                .get(row);
    }

    public void setDate(Date value) {
        HtmlInput input = (HtmlInput) getParam(2).getByXPath("./span/input").get(0);
        new DatetimeInputText(input).setValue(value);
    }

    public void setInteger(int value) {
        HtmlInput input = (HtmlInput) getParam(1).getByXPath("./input").get(0);
        new InputText(input).setValue(String.valueOf(value));
    }

    public void setList(List<String> value) {
        HtmlDivision div = (HtmlDivision) getParam(3).getByXPath("./input").get(0);
        new AutocompleteInputText(div).setValue(value);
    }

    public void setString(String value) {
        HtmlInput input = (HtmlInput) getParam(0).getByXPath("./input").get(0);
        new InputText(input).setValue(value);
    }

}