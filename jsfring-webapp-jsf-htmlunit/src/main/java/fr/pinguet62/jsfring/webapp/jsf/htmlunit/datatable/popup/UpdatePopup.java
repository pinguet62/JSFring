package fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;

/**
 * {@link Popup} for "Update" action.
 */
public interface UpdatePopup extends SubmitPopup {

    @Override
    default HtmlDivision getDialog() {
        return getPage().getFirstByXPath("//div[contains(@class, 'ui-dialog') and contains(@id, 'updateDialog')]");
    }

}
