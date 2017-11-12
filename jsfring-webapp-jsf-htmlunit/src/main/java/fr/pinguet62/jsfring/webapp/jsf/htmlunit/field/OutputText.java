package fr.pinguet62.jsfring.webapp.jsf.htmlunit.field;

import com.gargoylesoftware.htmlunit.html.HtmlSpan;

/**
 * {@link String#isEmpty() Blank String} must have {@code null} {@link Field#getValue() value}.
 */
public abstract class OutputText<T> extends ReadOnlyField<HtmlSpan, T> {

    public OutputText(HtmlSpan span) {
        super(span);
    }

}