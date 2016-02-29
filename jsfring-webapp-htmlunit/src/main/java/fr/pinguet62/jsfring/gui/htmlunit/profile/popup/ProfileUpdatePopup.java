package fr.pinguet62.jsfring.gui.htmlunit.profile.popup;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.UpdatePopup;
import fr.pinguet62.jsfring.gui.htmlunit.field.Field;
import fr.pinguet62.jsfring.gui.htmlunit.field.InputText;
import fr.pinguet62.jsfring.gui.htmlunit.field.PickList;
import fr.pinguet62.jsfring.gui.htmlunit.field.ReadWriteField;

public class ProfileUpdatePopup extends AbstractProfilePopup implements UpdatePopup {

    public ProfileUpdatePopup(HtmlPage page) {
        super(page);
    }

    public Field<?, ?> getRights() {
        return new PickList((HtmlDivision) getFieldTableCell(1).getByXPath("./div").get(0));
    }

    public ReadWriteField<?, String> getTitle() {
        return new InputText(
                (HtmlInput) getFieldTableCell(0).getByXPath("./input[contains(@class, 'ui-inputtext')]").get(0));
    }

}