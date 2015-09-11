package fr.pinguet62.jsfring.gui.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

/** @param <T> The content type. */
public abstract class Field<T> {

    protected final HtmlTableDataCell htmlTableDataCell;

    protected Field(HtmlTableDataCell htmlTableDataCell) {
        this.htmlTableDataCell = htmlTableDataCell;
    }

    /** Convert the {@link HtmlTableDataCell} to target value. */
    public abstract T getValue();

    public abstract boolean isReadonly();

}