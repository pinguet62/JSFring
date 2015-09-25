package fr.pinguet62.jsfring.gui.htmlunit.filter;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.sample.FilterPathBean;

public final class FilterPathPage extends AbstractPage {

    public FilterPathPage(HtmlPage page) {
        super(page);
    }

    /** @param i The line index. */
    public FilterField getFilterField(int i) {
        return new FilterField(page, i);
    }

    /** @see FilterPathBean#getNumberFilterDefault() */
    public FilterField getNumberFilterDefault() {
        return getFilterField(3);
    }

    /** @see FilterPathBean#getNumberFilterDoubleRange() */
    public FilterField getNumberFilterDoubleRange() {
        return getFilterField(5);
    }

    /** @see FilterPathBean#getNumberFilterLongRange() */
    public FilterField getNumberFilterLongRange() {
        return getFilterField(4);
    }

    /** @see FilterPathBean#getStringFilterDefault() */
    public FilterField getStringFilterDefault() {
        return getFilterField(0);
    }

    /** @see FilterPathBean#getStringFilterLength() */
    public FilterField getStringFilterLength() {
        return getFilterField(1);
    }

    /** @see FilterPathBean#getStringFilterRegex() */
    public FilterField getStringFilterRegex() {
        return getFilterField(2);
    }

}