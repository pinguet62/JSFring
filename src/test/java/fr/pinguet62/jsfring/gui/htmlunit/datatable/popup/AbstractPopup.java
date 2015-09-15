package fr.pinguet62.jsfring.gui.htmlunit.datatable.popup;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;

public abstract class AbstractPopup extends AbstractPage implements Popup {

    protected AbstractPopup(HtmlPage page) {
        super(page);
    }

    public void close() {
        HtmlAnchor x = (HtmlAnchor) getDialog().getByXPath(
                "./div[contains(@class, 'ui-dialog-titlebar')]/a[contains(@class, 'ui-dialog-titlebar-close')]").get(0);
        try {
            x.click();
            debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}