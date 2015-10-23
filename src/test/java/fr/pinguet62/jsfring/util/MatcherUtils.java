package fr.pinguet62.jsfring.util;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/** Utility class with simple {@link Matcher}s. */
public class MatcherUtils {

    /**
     * Check that value {@link String#matches(String) matches to regex}.
     *
     * @param regex The regex to check.
     * @return The result.
     */
    public static Matcher<String> matches(String regex) {
        return new TypeSafeMatcher<String>() {
            @Override
            public void describeTo(Description description) {}

            /** @see String#matches(String) */
            @Override
            protected boolean matchesSafely(String item) {
                return item.matches(regex);
            }
        };
    };

    // Utils class
    private MatcherUtils() {}

}