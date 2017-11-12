package fr.pinguet62.jsfring.webapp.jsf.htmlunit.filter;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage;

public final class FilterPathPage extends AbstractPage {

    public FilterPathPage(HtmlPage page) {
        super(page);
    }

    /**
     * Page contains several {@link FilterField}.<br>
     * This method gets the {@link FilterField} from it's index.
     *
     * @param i The line index.
     * @return The {@link FilterField}.
     */
    public FilterField getFilterField(int i) {
        return new FilterField(page, i);
    }

    public FilterField getNumberFilterDefault() {
        return getFilterField(2);
    }

    public FilterField getNumberFilterLongRange() {
        return getFilterField(3);
    }

    public FilterField getStringFilterDefault() {
        return getFilterField(0);
    }

    public FilterField getStringFilterRegex() {
        return getFilterField(1);
    }

}