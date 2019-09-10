package fr.pinguet62.jsfring.webapp.jsf.htmlunit.jasperreport;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.AutocompleteInputText;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.DatetimeInputText;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.InputText;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.String.valueOf;

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
                .getByXPath("//form//div[contains(@class, 'ui-panel')]/div[contains(@class, 'ui-panel-content')]/table/tbody/tr/td[2]")
                .get(row);
    }

    public void setDate(LocalDateTime value) {
        HtmlInput input = getParam(2).getFirstByXPath("./span/input");
        new DatetimeInputText(input).setValue(value);
    }

    public void setInteger(int value) {
        HtmlInput input = getParam(1).getFirstByXPath("./input");
        new InputText(input).setValue(valueOf(value));
    }

    public void setList(List<String> value) {
        HtmlDivision div = getParam(3).getFirstByXPath("./div");
        new AutocompleteInputText(div).setValue(value);
    }

    public void setString(String value) {
        HtmlInput input = getParam(0).getFirstByXPath("./input");
        new InputText(input).setValue(value);
    }

}
