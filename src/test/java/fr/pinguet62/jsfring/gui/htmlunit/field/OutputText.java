package fr.pinguet62.jsfring.gui.htmlunit.field;

import org.apache.commons.lang3.StringUtils;

import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

/**
 * {@link StringUtils#isBlank(CharSequence) Blank String} will have {@code null}
 * {@link Field#value value}.
 */
public final class OutputText extends ReadOnlyField<String> {

    public OutputText(HtmlTableDataCell htmlTableDataCell) {
        super(htmlTableDataCell);
    }

    @Override
    public String getValue() {
        return htmlTableDataCell.asText();
    }

}