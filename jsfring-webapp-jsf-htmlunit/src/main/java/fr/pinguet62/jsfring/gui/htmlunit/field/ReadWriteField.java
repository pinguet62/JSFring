package fr.pinguet62.jsfring.gui.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

public abstract class ReadWriteField<H extends HtmlElement, T> extends Field<H, T> {

    protected ReadWriteField(H html) {
        super(html);
    }

    @Override
    public boolean isReadonly() {
        return false;
    }

    public abstract void setValue(T value);

}