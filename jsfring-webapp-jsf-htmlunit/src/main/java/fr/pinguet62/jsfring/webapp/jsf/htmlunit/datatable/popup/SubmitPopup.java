package fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.NavigatorException;

import java.io.IOException;

import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.Delay.LONG;
import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.debug;

/**
 * {@link Popup} with "Submit" button.
 */
public interface SubmitPopup extends Popup {

    default void submit() {
        HtmlButton submit = getDialog().getFirstByXPath("./div[contains(@class, 'ui-dialog-content')]/form/button");
        try {
            HtmlPage page = submit.click();
            waitJS(LONG);
            debug(page); // TODO debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}
