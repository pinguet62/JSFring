package fr.pinguet62.jsfring.gui.htmlunit.datatable.popup;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;

public interface Popup {

    default void close() {
        HtmlAnchor x = (HtmlAnchor) getDialog().getByXPath(
                "./div[contains(@class, 'ui-dialog-titlebar')]/a[contains(@class, 'ui-dialog-titlebar-close')]").get(0);
        try {
            x.click();
            // TODO debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    HtmlDivision getDialog();

    HtmlPage getPage();

}