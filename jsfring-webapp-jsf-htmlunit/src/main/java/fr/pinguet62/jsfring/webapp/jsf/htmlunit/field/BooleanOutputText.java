package fr.pinguet62.jsfring.webapp.jsf.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlSpan;

public final class BooleanOutputText extends OutputText<Boolean> {

    public BooleanOutputText(HtmlSpan span) {
        super(span);
    }

    @Override
    public Boolean getValue() {
        String content = html.asText();
        if ("true".equals(content))
            return true;
        else if ("false".equals(content))
            return false;

        throw new IllegalArgumentException("Unknown boolean expression: " + content);
    }

}