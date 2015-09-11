package fr.pinguet62.jsfring.gui.htmlunit.user.popup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.datatable.UpdatePopup;
import fr.pinguet62.jsfring.gui.htmlunit.field.CheckBox;
import fr.pinguet62.jsfring.gui.htmlunit.field.Field;
import fr.pinguet62.jsfring.gui.htmlunit.field.InputText;

public final class UserUpdatePopup extends AbstractUserPopup implements UpdatePopup {

    public UserUpdatePopup(HtmlPage page) {
        super(page);
    }

    public Field<?> getEmail() {
        return new InputText(getFieldTableCell(1));
    }

    public Field<?> getProfiles() {
        throw new UnsupportedOperationException("TODO");
    }

    public Field<?> isActive() {
        return new CheckBox(getFieldTableCell(2));
    }

}