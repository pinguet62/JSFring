package fr.pinguet62.jsfring.gui.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

/**
 * @param <H> The html element type.
 * @param <T> The content type.
 */
public abstract class Field<H, T> {

    protected final H html;

    protected Field(H html) {
        this.html = html;
    }

    /** Convert the {@link HtmlTableDataCell} to target value. */
    public abstract T getValue();

    public abstract boolean isReadonly();

}