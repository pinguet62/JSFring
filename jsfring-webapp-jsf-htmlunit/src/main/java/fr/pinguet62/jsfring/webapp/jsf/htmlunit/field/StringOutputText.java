package fr.pinguet62.jsfring.webapp.jsf.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlSpan;

public final class StringOutputText extends OutputText<String> {

    public StringOutputText(HtmlSpan span) {
        super(span);
    }

    @Override
    public String getValue() {
        return html.asText();
    }

}