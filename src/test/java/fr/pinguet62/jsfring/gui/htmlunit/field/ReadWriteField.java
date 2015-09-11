package fr.pinguet62.jsfring.gui.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

public abstract class ReadWriteField<T> extends Field<T> {

    protected ReadWriteField(HtmlTableDataCell htmlTableDataCell) {
        super(htmlTableDataCell);
    }

    @Override
    public boolean isReadonly() {
        return false;
    }

    /** Convert the target value and update the {@link HtmlTableDataCell}. */
    public abstract void setValue(T value);

}
