package fr.pinguet62.jsfring.gui.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;

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

    /** Convert the {@link HtmlTableDataCell} to target value. */
    public abstract T getValue();

    public abstract boolean isReadonly();

}