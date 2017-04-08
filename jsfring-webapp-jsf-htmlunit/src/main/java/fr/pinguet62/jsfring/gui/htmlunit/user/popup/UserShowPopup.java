package fr.pinguet62.jsfring.gui.htmlunit.user.popup;

import java.util.Date;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.ShowPopup;
import fr.pinguet62.jsfring.gui.htmlunit.field.BooleanOutputText;
import fr.pinguet62.jsfring.gui.htmlunit.field.DateOutputText;
import fr.pinguet62.jsfring.gui.htmlunit.field.Field;
import fr.pinguet62.jsfring.gui.htmlunit.field.ListField;
import fr.pinguet62.jsfring.gui.htmlunit.field.StringOutputText;

public final class UserShowPopup extends AbstractUserPopup implements ShowPopup {

    public UserShowPopup(HtmlPage page) {
        super(page);
    }

    public Field<?, ?> getEmail() {
        return new StringOutputText((HtmlSpan) getFieldTableCell(1).getByXPath("./span").get(0));
    }

    public Field<?, Date> getLastConnection() {
        return new DateOutputText((HtmlSpan) getFieldTableCell(3).getByXPath("./span").get(0));
    }

    public Field<?, String> getLogin() {
        return new StringOutputText((HtmlSpan) getFieldTableCell(0).getByXPath("./span").get(0));
    }

    public Field<?, List<String>> getProfiles() {
        return new ListField((HtmlDivision) getFieldTableCell(4).getByXPath("./div[contains(@class, 'ui-datalist')]").get(0));
    }

    public Field<?, ?> isActive() {
        return new BooleanOutputText((HtmlSpan) getFieldTableCell(2).getByXPath("./span").get(0));
    }

}