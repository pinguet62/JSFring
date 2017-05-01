package fr.pinguet62.jsfring.webapp.jsf.htmlunit.field;

import java.util.Date;

import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import fr.pinguet62.jsfring.webapp.jsf.htmlunit.DateUtils;

public final class DateOutputText extends OutputText<Date> {

    public DateOutputText(HtmlSpan span) {
        super(span);
    }

    @Override
    public Date getValue() {
        return DateUtils.parseDateOrDateTime(html.asText());
    }

}