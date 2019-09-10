package fr.pinguet62.jsfring.webapp.jsf.htmlunit.user.popup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.UpdatePopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.CheckBox;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.DateOutputText;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.PickList;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.StringOutputText;

public final class UserUpdatePopup extends AbstractUserPopup implements UpdatePopup {

    public UserUpdatePopup(HtmlPage page) {
        super(page);
    }

    public CheckBox getActive() {
        return new CheckBox(getFieldTableCell(1).getFirstByXPath("./div[contains(@class, 'ui-chkbox')]"));
    }

    public StringOutputText getEmail() {
        return new StringOutputText(getFieldTableCell(0).getFirstByXPath("./span"));
    }

    public DateOutputText getLastConnection() {
        return new DateOutputText(getFieldTableCell(2).getFirstByXPath("./span"));
    }

    public PickList getProfiles() {
        return new PickList(getFieldTableCell(3).getFirstByXPath("./div"));
    }

}
