package fr.pinguet62.jsfring.webapp.jsf.htmlunit.field;

import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.DateUtils.DATETIME_FORMATTER;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.time.LocalDateTime;

import com.gargoylesoftware.htmlunit.html.HtmlSpan;

public final class DateOutputText extends OutputText<LocalDateTime> {

    public DateOutputText(HtmlSpan span) {
        super(span);
    }

    @Override
    public LocalDateTime getValue() {
        String value = html.asText();
        if (isBlank(value))
            return null;
        return LocalDateTime.parse(value, DATETIME_FORMATTER);
    }

}