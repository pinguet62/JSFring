package fr.pinguet62.jsfring.gui.htmlunit.filter;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.NavigatorException;

public final class FiltersPage extends AbstractPage {

    public FiltersPage(HtmlPage page) {
        super(page);
    }

    public FilterField getFilterNumber() {
        return new FilterField(page, 1);
    }

    public FilterField getFilterString() {
        return new FilterField(page, 0);
    }

    public void submit() {
        HtmlButton button = (HtmlButton) page.getByXPath("//form/button").get(0);
        try {
            page = button.click();
            waitJS();
            debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}