package fr.pinguet62.jsfring.gui.htmlunit.user.popup;

import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.UpdatePopup;
import fr.pinguet62.jsfring.gui.htmlunit.field.CheckBox;
import fr.pinguet62.jsfring.gui.htmlunit.field.InputText;
import fr.pinguet62.jsfring.gui.htmlunit.field.PickList;
import fr.pinguet62.jsfring.gui.htmlunit.field.ReadWriteField;

public final class UserCreatePopup extends AbstractUserPopup implements UpdatePopup {

    public UserCreatePopup(HtmlPage page) {
        super(page);
    }

    public ReadWriteField<?, Boolean> getActive() {
        return new CheckBox(
                (HtmlDivision) getFieldTableCell(2).getByXPath("./div[contains(@class, 'ui-chkbox')]").get(0));
    }

    public ReadWriteField<?, String> getEmail() {
        return new InputText(
                (HtmlInput) getFieldTableCell(1).getByXPath("./input[contains(@class, 'ui-inputtext')]").get(0));
    }

    public ReadWriteField<?, String> getLogin() {
        return new InputText((HtmlInput) getFieldTableCell(0).getByXPath("./span").get(0));
    }

    public ReadWriteField<?, List<String>> getProfiles() {
        return new PickList((HtmlDivision) getFieldTableCell(4).getByXPath("./div").get(0));
    }

}