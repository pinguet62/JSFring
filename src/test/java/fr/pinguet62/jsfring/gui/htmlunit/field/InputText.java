package fr.pinguet62.jsfring.gui.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlInput;

import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;

public final class InputText extends ReadWriteField<HtmlInput, String> {

    public InputText(HtmlInput input) {
        super(input);
    }

    @Override
    public String getValue() {
        return html.asText();
    }

    @Override
    public void setValue(String value) {
        html.setValueAttribute(value);
        AbstractPage.debug(html);
    }

}