package fr.pinguet62.jsfring.webapp.jsf.htmlunit.user.popup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.CreatePopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.CheckBox;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.InputText;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.PickList;

public final class UserCreatePopup extends AbstractUserPopup implements CreatePopup {

    public UserCreatePopup(HtmlPage page) {
        super(page);
    }

    public CheckBox getActive() {
        return new CheckBox(getFieldTableCell(1).getFirstByXPath("./div[contains(@class, 'ui-chkbox')]"));
    }

    public InputText getEmail() {
        return new InputText(getFieldTableCell(0).getFirstByXPath("./input[contains(@class, 'ui-inputtext')]"));
    }

    public PickList getProfiles() {
        return new PickList(getFieldTableCell(2).getFirstByXPath("./div"));
    }

}
