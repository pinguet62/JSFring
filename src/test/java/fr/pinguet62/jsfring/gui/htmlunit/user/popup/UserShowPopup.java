package fr.pinguet62.jsfring.gui.htmlunit.user.popup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.datatable.ShowPopup;
import fr.pinguet62.jsfring.gui.htmlunit.field.Field;
import fr.pinguet62.jsfring.gui.htmlunit.field.ListField;
import fr.pinguet62.jsfring.gui.htmlunit.field.OutputText;

public final class UserShowPopup extends AbstractUserPopup implements ShowPopup {

    public UserShowPopup(HtmlPage page) {
        super(page);
    }

    public Field<?> getEmail() {
        return new OutputText(getFieldTableCell(1));
    }

    public Field<?> getProfiles() {
        return new ListField(getFieldTableCell(4));
    }

    public Field<?> isActive() {
        return new OutputText(getFieldTableCell(2));
    }

}