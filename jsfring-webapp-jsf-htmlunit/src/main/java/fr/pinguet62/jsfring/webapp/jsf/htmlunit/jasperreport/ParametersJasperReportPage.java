package fr.pinguet62.jsfring.webapp.jsf.htmlunit.jasperreport;

import static java.lang.String.valueOf;

import java.time.LocalDateTime;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.AutocompleteInputText;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.DatetimeInputText;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.InputText;

public final class ParametersJasperReportPage extends AbstractJasperReportPage {

    public ParametersJasperReportPage(HtmlPage page) {
        super(page);
    }

    /**
     * @param row The param index.
     * @return The {@link HtmlTableDataCell} who contains the parameter field.
     */
    private HtmlTableDataCell getParam(int row) {
        return (HtmlTableDataCell) page
                .getByXPath(
                        "//form//div[contains(@class, 'ui-panel')]/div[contains(@class, 'ui-panel-content')]/table/tbody/tr/td[2]")
                .get(row);
    }

    public void setDate(LocalDateTime value) {
        HtmlInput input = (HtmlInput) getParam(2).getByXPath("./span/input").get(0);
        new DatetimeInputText(input).setValue(value);
    }

    public void setInteger(int value) {
        HtmlInput input = (HtmlInput) getParam(1).getByXPath("./input").get(0);
        new InputText(input).setValue(valueOf(value));
    }

    public void setList(List<String> value) {
        HtmlDivision div = (HtmlDivision) getParam(3).getByXPath("./div").get(0);
        new AutocompleteInputText(div).setValue(value);
    }

    public void setString(String value) {
        HtmlInput input = (HtmlInput) getParam(0).getByXPath("./input").get(0);
        new InputText(input).setValue(value);
    }

}