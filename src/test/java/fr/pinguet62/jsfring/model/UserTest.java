package fr.pinguet62.jsfring.model;

import static fr.pinguet62.jsfring.model.User.EMAIL_REGEX;
import static fr.pinguet62.jsfring.model.User.PASSWORD_REGEX;
import static fr.pinguet62.jsfring.util.MatcherUtils.matches;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import fr.pinguet62.jsfring.util.Combinator;

/** @see User */
public final class UserTest {

    /** Generates */
    private List<String> forAllCombinaisonOf(String... characters) {
        return new Combinator<String>(asList(characters)).get().stream().map(list -> list.stream().collect(joining("")))
                .collect(toList());
    }

    /** @see User#EMAIL_REGEX */
    @Test
    public void test_email_regex() {
        assertTrue("username@host.domain".matches(EMAIL_REGEX));

        assertFalse("@host.domain".matches(EMAIL_REGEX));
        assertFalse("usernamehost.domain".matches(EMAIL_REGEX));
        assertFalse("username@.domain".matches(EMAIL_REGEX));
        // assertFalse("username@hostdomain".matches(EMAIL_REGEX));
        assertFalse("username@host.".matches(EMAIL_REGEX));
        assertFalse("".matches(EMAIL_REGEX));
    }

    /** @see User#equals(Object) */
    @Test
    public void test_equals() {
        assertEquals(new User(), new User());
        assertEquals(new User("same login"), new User("same login"));
        assertEquals(new User("same login", null, null), new User("same login", null, null));
        assertEquals(new User("same login", "AAA", "BBB"), new User("same login", "111", "222"));

        assertNotEquals(new User("an id", null, null), new User("other value", null, null));
        assertNotEquals(new User(), "other type");
    }

    /** @see User#PASSWORD_REGEX */
    @Test
    public void test_password_regex_error() {
        // < 6 character
        assertThat(forAllCombinaisonOf("a", "b", "c", "1", "$"), everyItem(not(matches(PASSWORD_REGEX))));
        // 0 letter
        assertThat(forAllCombinaisonOf("a", "b", "c", "d", "e", "f", "$", "#", "~"), everyItem(not(matches(PASSWORD_REGEX))));
        // 0 special
        assertThat(forAllCombinaisonOf("1", "2", "3", "a", "b", "c", "d", "e", "f"), everyItem(not(matches(PASSWORD_REGEX))));
    }

    /** @see User#PASSWORD_REGEX */
    @Test
    public void test_password_regex_ok() {
        // 1 letter + 1 special + 6 character
        assertThat(forAllCombinaisonOf("5", "$", "a", "b", "c", "d", "e", "f"), everyItem(matches(PASSWORD_REGEX)));
        // >1 letter + >1 special + >6 character
        assertThat(forAllCombinaisonOf("5", "6", "$", "^", "a", "b", "c", "d", "e", "f"), everyItem(matches(PASSWORD_REGEX)));
    }

}