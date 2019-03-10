package fr.pinguet62.jsfring.webapp.jsf.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage;

/**
 * @param <H> The html element type.
 * @param <T> The content type.
 */
public abstract class Field<H extends HtmlElement, T> extends AbstractPage {

    protected final H html;

    protected Field(H html) {
        super(html.getHtmlPageOrNull());
        this.html = html;
    }

    public abstract T getValue();

    public abstract boolean isReadonly();

}
