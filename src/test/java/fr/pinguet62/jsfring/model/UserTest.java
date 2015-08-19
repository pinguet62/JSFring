package fr.pinguet62.jsfring.model;

import static fr.pinguet62.jsfring.model.User.PASSWORD_REGEX;
import static java.util.stream.Collectors.joining;

import java.util.Arrays;

import org.junit.Test;

import fr.pinguet62.util.Combinator;

/** @see User */
public final class UserTest {

    @Test
    public void test_password_regex() {
        new Combinator<String>(Arrays.asList("5", "$", "a", "b", "c", "d", "e",
                "f")).get().stream()
                .map(list -> list.stream().collect(joining("")))
                .allMatch(password -> password.matches(PASSWORD_REGEX));
    }

}