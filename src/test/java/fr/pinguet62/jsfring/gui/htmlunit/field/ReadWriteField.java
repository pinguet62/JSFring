package fr.pinguet62.jsfring.gui.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

public abstract class ReadWriteField<H, T> extends Field<H, T> {

    protected ReadWriteField(H html) {
        super(html);
    }

    @Override
    public boolean isReadonly() {
        return false;
    }

    /** Convert the target value and update the {@link HtmlTableDataCell}. */
    public abstract void setValue(T value);

}