package fr.pinguet62.jsfring.webapp.jsf.htmlunit.right.popup;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup.UpdatePopup;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.field.InputText;

public final class RightUpdatePopup extends AbstractRightPopup implements UpdatePopup {

    public RightUpdatePopup(HtmlPage page) {
        super(page);
    }

    public InputText getTitle() {
        return new InputText(getFieldTableCell(1).getFirstByXPath("./input[contains(@class, 'ui-inputtext')]"));
    }

}
