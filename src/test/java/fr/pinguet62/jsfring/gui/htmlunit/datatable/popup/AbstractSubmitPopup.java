package fr.pinguet62.jsfring.gui.htmlunit.datatable.popup;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;

public abstract class AbstractSubmitPopup extends AbstractPopup implements UpdatePopup {

    protected AbstractSubmitPopup(HtmlPage page) {
        super(page);
    }

    public void submit() {
        HtmlButton button = (HtmlButton) getDialog().getByXPath("./div[contains(@class, 'ui-dialog-content')]/form/button")
                .get(0);
        try {
            button.click();
            waitJS();
            debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}