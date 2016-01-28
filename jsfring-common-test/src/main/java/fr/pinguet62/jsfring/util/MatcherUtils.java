package fr.pinguet62.jsfring.util;

import java.util.Comparator;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/** Utility class with simple {@link Matcher}s. */
public final class MatcherUtils {

    /**
     * Check that {@link List} is sorted.
     *
     * @param <T> The type of elements.
     * @param comparator The {@link Comparator} used by algorithm.
     * @return The {@link Matcher}.
     */
    public static <T> Matcher<List<T>> isSorted(Comparator<T> comparator) {
        return new TypeSafeMatcher<List<T>>() {
            @Override
            public void describeTo(Description description) {}

            /**
             * @param values The {@link List} to check.
             * @throws ClassCastException If the list contains elements that are
             *             not {@link Comparable}.
             */
            @Override
            protected boolean matchesSafely(List<T> values) {
                for (int i = 0, j = 1; j < values.size(); i++, j++) {
                    if (0 < comparator.compare(values.get(i), values.get(j)))
                        return false;
                }
                return true;
            }
        };
    };

    /**
     * Check that value {@link String#matches(String) matches to regex}.
     *
     * @param regex The regex.
     * @return The {@link Matcher}.
     * @see String#matches(String)
     */
    public static Matcher<String> matches(String regex) {
        return new TypeSafeMatcher<String>() {
            @Override
            public void describeTo(Description description) {}

            /**
             * @param value The {@link String} to test.
             * @see String#matches(String)
             */
            @Override
            protected boolean matchesSafely(String value) {
                return value.matches(regex);
            }
        };
    }

    // Utils class
    private MatcherUtils() {}

}