package fr.pinguet62.jsfring.webapp.jsf.htmlunit.user.popup;

import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.CreatePopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.CheckBox;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.InputText;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.PickList;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.ReadWriteField;

public final class UserCreatePopup extends AbstractUserPopup implements CreatePopup {

    public UserCreatePopup(HtmlPage page) {
        super(page);
    }

    public ReadWriteField<?, Boolean> getActive() {
        return new CheckBox((HtmlDivision) getFieldTableCell(1).getByXPath("./div[contains(@class, 'ui-chkbox')]").get(0));
    }

    public ReadWriteField<?, String> getEmail() {
        return new InputText((HtmlInput) getFieldTableCell(0).getByXPath("./input[contains(@class, 'ui-inputtext')]").get(0));
    }

    public ReadWriteField<?, List<String>> getProfiles() {
        return new PickList((HtmlDivision) getFieldTableCell(2).getByXPath("./div").get(0));
    }

}