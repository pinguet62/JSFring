package fr.pinguet62.jsfring.webapp.jsf.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlInput;

import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.Delay.SHORT;

public final class InputText extends ReadWriteField<HtmlInput, String> {

    public InputText(HtmlInput input) {
        super(input);
    }

    @Override
    public String getValue() {
        return html.asText();
    }

    public boolean isError() {
        return html.getAttribute("class").contains("ui-state-error");
    }

    @Override
    public void setValue(String value) {
        html.setValueAttribute(value);
        waitJS(SHORT);
        debug();
    }

}