package fr.pinguet62.jsfring.webapp.jsf.htmlunit.right.popup;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.UpdatePopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.InputText;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.ReadWriteField;

public final class RightUpdatePopup extends AbstractRightPopup implements UpdatePopup {

    public RightUpdatePopup(HtmlPage page) {
        super(page);
    }

    public ReadWriteField<?, String> getTitle() {
        return new InputText((HtmlInput) getFieldTableCell(1).getByXPath("./input[contains(@class, 'ui-inputtext')]").get(0));
    }

}