package fr.pinguet62.jsfring.webapp.jsf.htmlunit.user.popup;

import java.time.LocalDateTime;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.ShowPopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.BooleanOutputText;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.DateOutputText;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.Field;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.ListField;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.StringOutputText;

public final class UserShowPopup extends AbstractUserPopup implements ShowPopup {

    public UserShowPopup(HtmlPage page) {
        super(page);
    }

    public Field<?, ?> getEmail() {
        return new StringOutputText((HtmlSpan) getFieldTableCell(0).getByXPath("./span").get(0));
    }

    public Field<?, LocalDateTime> getLastConnection() {
        return new DateOutputText((HtmlSpan) getFieldTableCell(2).getByXPath("./span").get(0));
    }

    public Field<?, List<String>> getProfiles() {
        return new ListField((HtmlDivision) getFieldTableCell(3).getByXPath("./div[contains(@class, 'ui-datalist')]").get(0));
    }

    public Field<?, ?> isActive() {
        return new BooleanOutputText((HtmlSpan) getFieldTableCell(1).getByXPath("./span").get(0));
    }

}