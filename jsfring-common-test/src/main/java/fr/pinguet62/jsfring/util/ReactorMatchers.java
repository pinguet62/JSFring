package fr.pinguet62.jsfring.util;

import lombok.experimental.UtilityClass;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import reactor.core.publisher.Flux;

@UtilityClass
public class ReactorMatchers {

    /**
     * @see Flux#count()
     */
    public <T> Matcher<Flux<T>> hasCount(long count) {
        return new TypeSafeMatcher<Flux<T>>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(Flux<T> flux) {
                return flux.count().block() == count;
            }
        };
    }

}
