package fr.pinguet62.jsfring.gui.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

public abstract class ReadOnlyField<T> extends Field<T> {

    protected ReadOnlyField(HtmlTableDataCell htmlTableDataCell) {
        super(htmlTableDataCell);
    }

    @Override
    public boolean isReadonly() {
        return true;
    }

}