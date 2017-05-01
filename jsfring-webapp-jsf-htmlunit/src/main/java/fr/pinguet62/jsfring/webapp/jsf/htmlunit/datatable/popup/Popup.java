package fr.pinguet62.jsfring.webapp.jsf.htmlunit.datatable.popup;

import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.debug;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.webapp.jsf.htmlunit.NavigatorException;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.Delay;

public interface Popup {

    default void close() {
        HtmlAnchor x = (HtmlAnchor) getDialog()
                .getByXPath("./div[contains(@class, 'ui-dialog-titlebar')]/a[contains(@class, 'ui-dialog-titlebar-close')]")
                .get(0);
        try {
            HtmlPage page = x.click();
            debug(page); // TODO debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

    HtmlDivision getDialog();

    HtmlPage getPage();

    void waitJS(Delay delay);

}