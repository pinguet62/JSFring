package fr.pinguet62.jsfring.webapp.jsf.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

public abstract class ReadOnlyField<H extends HtmlElement, T> extends Field<H, T> {

    protected ReadOnlyField(H html) {
        super(html);
    }

    @Override
    public boolean isReadonly() {
        return true;
    }

}