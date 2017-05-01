package fr.pinguet62.jsfring.webapp.jsf.htmlunit.profile.popup;

import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.CreatePopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.InputText;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.PickList;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.ReadWriteField;

public final class ProfileCreatePopup extends AbstractProfilePopup implements CreatePopup {

    public ProfileCreatePopup(HtmlPage page) {
        super(page);
    }

    public ReadWriteField<?, List<String>> getRights() {
        return new PickList((HtmlDivision) getFieldTableCell(1).getByXPath("./div").get(0));
    }

    public ReadWriteField<?, String> getTitle() {
        return new InputText(
                (HtmlInput) getFieldTableCell(0).getByXPath("./input[contains(@class, 'ui-inputtext')]").get(0));
    }

}