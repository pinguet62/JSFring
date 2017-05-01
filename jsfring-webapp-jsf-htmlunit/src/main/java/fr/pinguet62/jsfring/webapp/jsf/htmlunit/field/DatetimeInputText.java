package fr.pinguet62.jsfring.webapp.jsf.htmlunit.field;

import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.DateUtils.DATETIME_FORMATTER;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.javascript.host.event.KeyboardEvent;

import fr.pinguet62.jsfring.webapp.jsf.htmlunit.NavigatorException;

public final class DatetimeInputText extends ReadWriteField<HtmlInput, Date> {

    public DatetimeInputText(HtmlInput html) {
        super(html);
    }

    @Override
    public Date getValue() {
        try {
            return DATETIME_FORMATTER.parse(html.asText());
        } catch (ParseException e) {
            throw new NavigatorException(e);
        }
    }

    /**
     * The user input must only be number, without {@code "/"} and {@code ":"}.
     */
    @Override
    public void setValue(Date value) {
        String formatted = DATETIME_FORMATTER.format(value);
        String userInput = " " + formatted.replaceAll("[^0-9]", "");

        try {
            html.type(KeyboardEvent.DOM_VK_DELETE);
            html.type(userInput);
            debug();
        } catch (IOException e) {
            throw new NavigatorException(e);
        }
    }

}