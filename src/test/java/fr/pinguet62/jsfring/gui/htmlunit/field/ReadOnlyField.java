package fr.pinguet62.jsfring.gui.htmlunit.field;

public abstract class ReadOnlyField<H, T> extends Field<H, T> {

    protected ReadOnlyField(H html) {
        super(html);
    }

    @Override
    public boolean isReadonly() {
        return true;
    }

}