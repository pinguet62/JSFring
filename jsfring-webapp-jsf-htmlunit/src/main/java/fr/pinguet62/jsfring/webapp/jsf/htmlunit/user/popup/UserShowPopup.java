package fr.pinguet62.jsfring.webapp.jsf.htmlunit.user.popup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.ShowPopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.BooleanOutputText;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.DateOutputText;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.Field;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.ListField;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.StringOutputText;

import java.time.LocalDateTime;
import java.util.List;

public final class UserShowPopup extends AbstractUserPopup implements ShowPopup {

    public UserShowPopup(HtmlPage page) {
        super(page);
    }

    public Field<?, ?> getEmail() {
        return new StringOutputText(getFieldTableCell(0).getFirstByXPath("./span"));
    }

    public Field<?, LocalDateTime> getLastConnection() {
        return new DateOutputText(getFieldTableCell(2).getFirstByXPath("./span"));
    }

    public Field<?, List<String>> getProfiles() {
        return new ListField(getFieldTableCell(3).getFirstByXPath("./div[contains(@class, 'ui-datalist')]"));
    }

    public Field<?, ?> isActive() {
        return new BooleanOutputText(getFieldTableCell(1).getFirstByXPath("./span"));
    }

}
