package fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;

/** {@link Popup} for "Show" action. */
public interface ShowPopup extends Popup {

    @Override
    default HtmlDivision getDialog() {
        return (HtmlDivision) getPage().getByXPath("//div[contains(@class, 'ui-dialog') and contains(@id, 'showDialog')]")
                .get(0);
    }

}