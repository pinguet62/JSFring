package fr.pinguet62.jsfring.gui.htmlunit.field;

import org.apache.commons.lang3.StringUtils;

import com.gargoylesoftware.htmlunit.html.HtmlSpan;

/**
 * {@link StringUtils#isBlank(CharSequence) Blank String} will have {@code null}
 * {@link Field#value value}.
 */
public final class OutputText extends ReadOnlyField<HtmlSpan, String> {

    public OutputText(HtmlSpan span) {
        super(span);
    }

    @Override
    public String getValue() {
        return html.asText();
    }

}