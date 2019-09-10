package fr.pinguet62.jsfring.webapp.jsf.htmlunit.profile.popup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.UpdatePopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.InputText;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.PickList;

public class ProfileUpdatePopup extends AbstractProfilePopup implements UpdatePopup {

    public ProfileUpdatePopup(HtmlPage page) {
        super(page);
    }

    public PickList getRights() {
        return new PickList(getFieldTableCell(1).getFirstByXPath("./div"));
    }

    public InputText getTitle() {
        return new InputText(getFieldTableCell(0).getFirstByXPath("./input[contains(@class, 'ui-inputtext')]"));
    }

}
