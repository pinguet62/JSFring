package fr.pinguet62.jsfring.gui.htmlunit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public final class DateUtils {

    public static final DateFormat DATETIME_FORMATTER = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    /**
     * Test if the 2 {@link Date} have the same second.
     *
     * @return The result.<br>
     *         {@code true} if the 2 parameters are {@code null}.<br>
     *         {@code false} if 1 of the 2 parameters is {@code null}.
     */
    public static boolean equalsSecond(Date d1, Date d2) {
        if (d1 == null && d2 == null)
            return true;
        if (d1 == null || d2 == null)
            return false;

        int field = Calendar.SECOND;
        Date s1 = org.apache.commons.lang3.time.DateUtils.truncate(d1, field);
        Date s2 = org.apache.commons.lang3.time.DateUtils.truncate(d2, field);
        return s1.equals(s2);
    }

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
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * @param value
     * @return The {@link Date}.<br>
     *         {@code null} if empty value.
     * @throws RuntimeException Invalid/Unknown date format.
     */
    public static Date parseDateOrDateTime(String value) {
        if (StringUtils.isBlank(value))
            return null;

        try {
            if (value.length() == 10) // Date
                return new SimpleDateFormat("dd/MM/yyyy").parse(value);
            else if (value.length() == 19) // Datetime
                return DATETIME_FORMATTER.parse(value);

            throw new RuntimeException("Unknown date format: " + value);
        } catch (ParseException exception) {
            throw new RuntimeException("Invalid date format: " + value, exception);
        }
    }

    // Util class
    private DateUtils() {}

}