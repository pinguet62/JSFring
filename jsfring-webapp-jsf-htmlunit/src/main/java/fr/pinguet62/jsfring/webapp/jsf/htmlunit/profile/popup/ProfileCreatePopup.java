package fr.pinguet62.jsfring.webapp.jsf.htmlunit.profile.popup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.CreatePopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.InputText;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.PickList;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.ReadWriteField;

public final class ProfileCreatePopup extends AbstractProfilePopup implements CreatePopup {

    public ProfileCreatePopup(HtmlPage page) {
        super(page);
    }

    public PickList getRights() {
        return new PickList(getFieldTableCell(1).getFirstByXPath("./div"));
    }

    public ReadWriteField<?, String> getTitle() {
        return new InputText(getFieldTableCell(0).getFirstByXPath("./input[contains(@class, 'ui-inputtext')]"));
    }

}
