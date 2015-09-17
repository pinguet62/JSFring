package fr.pinguet62.jsfring.gui.htmlunit.datatable.popup;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.html.HtmlButton;

import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;

/** {@link Popup} with "Submit" button. */
public interface SubmitPopup extends Popup {

    default void submit() {
        HtmlButton button = (HtmlButton) getDialog().getByXPath("./div[contains(@class, 'ui-dialog-content')]/form/button")
                .get(0);
        try {
            button.click();
            // TODO waitJS();
            // TODO debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}