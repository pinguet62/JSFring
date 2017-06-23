package fr.pinguet62.jsfring.webapp.jsf.htmlunit;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.format.DateTimeFormatter;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtils {

    public static final DateTimeFormatter DATE_FORMATTER = ofPattern("MM/dd/yyyy");

    public static final DateTimeFormatter DATETIME_FORMATTER = ofPattern("MM/dd/yyyy HH:mm:ss");

}