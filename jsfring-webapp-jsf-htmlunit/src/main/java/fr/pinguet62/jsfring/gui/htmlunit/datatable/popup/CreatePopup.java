package fr.pinguet62.jsfring.gui.htmlunit.datatable.popup;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;

/** {@link Popup} for "Create" action. */
public interface CreatePopup extends SubmitPopup {

    @Override
    default HtmlDivision getDialog() {
        return (HtmlDivision) getPage()
                .getByXPath("//div[contains(@class, 'ui-dialog') and contains(@id, 'createDialog')]").get(0);
    }

}