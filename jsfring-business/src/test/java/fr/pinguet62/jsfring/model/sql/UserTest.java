package fr.pinguet62.jsfring.model.sql;

import fr.pinguet62.jsfring.common.Combinator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.pinguet62.jsfring.model.sql.User.*;
import static fr.pinguet62.jsfring.util.MatcherUtils.matches;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * @see User
 */
public final class UserTest {

    /**
     * Generates
     */
    private List<String> forAllCombinaisonOf(String... characters) {
        return new Combinator<>(asList(characters)).get().stream().map(list -> list.stream().collect(joining(""))).collect(toList());
    }

    /**
     * @see User#EMAIL_REGEX
     */
    @Test
    public void test_email_regex() {
        assertThat("username@host.domain", matches(EMAIL_REGEX));

        assertThat("@host.domain", not(matches(EMAIL_REGEX)));
        assertThat("usernamehost.domain", not(matches(EMAIL_REGEX)));
        assertThat("username@.domain", not(matches(EMAIL_REGEX)));
        // assertThat("username@hostdomain", not(matches(EMAIL_REGEX))));
        assertThat("username@host.", not(matches(EMAIL_REGEX)));
        assertThat("", not(matches(EMAIL_REGEX)));
    }

    /**
     * @see User#equals(Object)
     */
    @Test
    public void test_equals() {
        assertThat(builder().build(), is(equalTo(builder().build())));
        assertThat(builder().email("same@email.fr").build(), is(equalTo(builder().email("same@email.fr").build())));
        assertThat(builder().email("same@email.fr").build(), is(equalTo(builder().email("same@email.fr").build())));
        assertThat(builder().email("same@email.fr").password("AAA").active(true).build(), is(equalTo(builder().email("same@email.fr").password("111").active(true).build())));

        assertThat(builder().email("an@email.fr").build(), is(not(equalTo(builder().email("another@email.fr").build()))));
        assertThat(builder().build(), is(not(equalTo("other type"))));
    }

    /**
     * @see User#PASSWORD_REGEX
     */
    @Test
    public void test_password_regex_error() {
        // < 6 character
        assertThat(forAllCombinaisonOf("a", "b", "c", "1", "$"), everyItem(not(matches(PASSWORD_REGEX))));
        // 0 letter
        assertThat(forAllCombinaisonOf("a", "b", "c", "d", "e", "f", "$", "#", "~"), everyItem(not(matches(PASSWORD_REGEX))));
        // 0 special
        assertThat(forAllCombinaisonOf("1", "2", "3", "a", "b", "c", "d", "e", "f"), everyItem(not(matches(PASSWORD_REGEX))));
    }

    /**
     * @see User#PASSWORD_REGEX
     */
    @Test
    public void test_password_regex_ok() {
        // 1 letter + 1 special + 6 character
        assertThat(forAllCombinaisonOf("5", "$", "a", "b", "c", "d", "e", "f"), everyItem(matches(PASSWORD_REGEX)));
        // >1 letter + >1 special + >6 character
        assertThat(forAllCombinaisonOf("5", "6", "$", "^", "a", "b", "c", "d", "e", "f"), everyItem(matches(PASSWORD_REGEX)));
    }

}