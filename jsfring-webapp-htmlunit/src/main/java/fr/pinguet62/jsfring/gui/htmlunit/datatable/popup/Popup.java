package fr.pinguet62.jsfring.gui.htmlunit.datatable.popup;

import static fr.pinguet62.jsfring.gui.htmlunit.AbstractPage.debug;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage.Delay;
import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;

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