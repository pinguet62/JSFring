package fr.pinguet62.jsfring.gui.htmlunit;

import static java.util.Calendar.DATE;
import static java.util.Calendar.HOUR;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils {

    public static final DateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy");

    public static final DateFormat DATETIME_FORMATTER = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

    /**
     * @param year The year (without offset).
     * @param month The month: from {@code 1} to {@code 12}.
     * @param day The day of month: from {@code 1} to {@code 28} or {@code 31}.
     * @return The {@link Date}.
     */
    public static Date getDate(int year, int month, int day) {
        return getDatetime(year, month, day, 0, 0, 0);
    }

    /**
     * @param year The year (without offset).
     * @param month The month: from {@code 1} to {@code 12}.
     * @param day The day of month: from {@code 1} to {@code 28} or {@code 31}.
     * @param hour The hour: from {@code 0} to {@code 23}.
     * @param minute The minutes: from {@code 0} to {@code 59}.
     * @param second The second: from {@code 0} to {@code 59}.
     * @return The {@link Date}.
     */
    public static Date getDatetime(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = getInstance();
        calendar.clear();
        calendar.set(YEAR, year);
        calendar.set(MONTH, month - 1);
        calendar.set(DATE, day);
        calendar.set(HOUR, hour);
        calendar.set(MINUTE, minute);
        calendar.set(SECOND, second);
        return calendar.getTime();
    }

    /**
     * Parse the formatted {@link Date}, with time or not.
     *
     * @param value The {@link String} value.
     * @return The {@link Date}.<br>
     *         {@code null} if empty value.
     * @throws IllegalArgumentException Invalid/Unknown date format.
     */
    public static Date parseDateOrDateTime(String value) {
        if (isBlank(value))
            return null;

        try {
            if (value.length() == 10) // Date
                return DATE_FORMATTER.parse(value);
            else if (value.length() == 19) // Datetime
                return DATETIME_FORMATTER.parse(value);

            throw new IllegalArgumentException("Unknown date format: " + value);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + value, e);
        }
    }

    // Util class
    private DateUtils() {}

}