package frpinguet62.jsfring.util;

import static fr.pinguet62.jsfring.util.MatcherUtils.equalToTruncated;
import static fr.pinguet62.jsfring.util.MatcherUtils.equalWithoutOrderTo;
import static fr.pinguet62.jsfring.util.MatcherUtils.mappedTo;
import static fr.pinguet62.jsfring.util.MatcherUtils.matches;
import static fr.pinguet62.jsfring.util.MatcherUtils.parameter;
import static fr.pinguet62.jsfring.util.MatcherUtils.sorted;
import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.of;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Comparator;

import org.hamcrest.Matcher;
import org.junit.Test;

import fr.pinguet62.jsfring.util.MatcherUtils;

/** @see MatcherUtils */
public class MatcherUtilsTest {

    /** @see MatcherUtils#equalToTruncated(LocalDateTime, TemporalUnit) */
    @Test
    public void test_equalToTruncated() {
        // assertThat(of(2016, 1, 2, 10, 20, 30), is(equalToTruncated(of(2016, 1, 2, 10, 29, 39), YEARS)));
        // assertThat(of(2016, 1, 2, 10, 20, 30), is(equalToTruncated(of(2016, 1, 2, 10, 29, 39), MONTHS)));
        assertThat(of(2016, 1, 2, 10, 20, 30), is(equalToTruncated(of(2016, 1, 2, 10, 29, 39), DAYS)));
        assertThat(of(2016, 1, 2, 10, 20, 30), is(not(equalToTruncated(of(2016, 1, 2, 19, 29, 39), HOURS))));
        assertThat(of(2016, 1, 2, 10, 20, 30), is(not(equalToTruncated(of(2016, 1, 2, 19, 29, 39), MINUTES))));
        assertThat(of(2016, 1, 2, 10, 20, 30), is(not(equalToTruncated(of(2016, 1, 2, 19, 29, 39), SECONDS))));
    }

    /** @see MatcherUtils#equalToTruncated(LocalDateTime, TemporalUnit) */
    @Test
    public void test_equalToTruncated_null() {
        Iterable<Runnable> tests = asList(//
                () -> assertThat("expected==null", now(), is(not(equalToTruncated(null, SECONDS)))), //
                () -> assertThat("actual==null", now(), is(not(equalToTruncated(null, SECONDS)))) //
                );
        try {
            tests.forEach(Runnable::run);
            fail();
        } catch (AssertionError e) {
        }
    }

    /** @see MatcherUtils#equalWithoutOrderTo(Iterable) */
    @Test
    public void test_equalWithoutOrderTo() {
        assertThat(asList(), is(equalWithoutOrderTo(asList())));
        assertThat(asList(42), is(equalWithoutOrderTo(asList(42))));
        assertThat(asList(1, 2, 3), is(equalWithoutOrderTo(asList(3, 2, 1))));
        assertThat(asList(1, 2, 3), is(not(equalWithoutOrderTo(asList(7, 8, 9)))));
    }

    /** @see MatcherUtils#sorted(Comparator) */
    @Test
    public void test_mappedTo() {
        assertThat(asList("1", "22", "333"), everyItem(mappedTo(String::length, is(greaterThan(0)))));
        assertThat(asList("1", "22", "333"), not(everyItem(mappedTo(String::length, is(greaterThan(2))))));
    }

    /** @see MatcherUtils#matches(String) */
    @Test
    public void test_matches() {
        assertThat("42", matches("[0-9]+"));
        assertThat("abc", not(matches("[0-9]+")));
    }

    /** @see MatcherUtils#parameter(String, Matcher) */
    @Test
    public void test_parameter() throws MalformedURLException {
        assertThat("param1=value1&param2=value2", parameter("param1", is(equalTo("value1"))));
        assertThat("param1=value1&param2=value2", parameter("param2", is(equalTo("value2"))));

        assertThat("param1=value1&param2=value2", parameter("param3", is(nullValue())));

        assertThat("", parameter("param", is(nullValue())));
    }

    /** @see MatcherUtils#sorted(Comparator) */
    @Test
    public void test_sorted() {
        assertThat(asList(1, 2, 3, 4), is(sorted(Integer::compareTo)));
        assertThat(asList(1, 9, 2, 8), is(not(sorted(Integer::compareTo))));
        assertThat(asList(9, 8, 7, 6), is(not(sorted(Integer::compareTo))));
    }

}