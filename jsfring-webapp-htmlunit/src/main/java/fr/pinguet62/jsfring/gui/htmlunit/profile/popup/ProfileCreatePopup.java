package fr.pinguet62.jsfring.gui.htmlunit.profile.popup;

import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.datatable.popup.UpdatePopup;
import fr.pinguet62.jsfring.gui.htmlunit.field.InputText;
import fr.pinguet62.jsfring.gui.htmlunit.field.PickList;
import fr.pinguet62.jsfring.gui.htmlunit.field.ReadWriteField;

public final class ProfileCreatePopup extends AbstractProfilePopup implements UpdatePopup {

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