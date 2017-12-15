package fr.pinguet62.jsfring.util;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import static fr.pinguet62.jsfring.util.ReactorMatchers.hasCount;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * @see ReactorMatchers
 */
public class ReactorMatchersTest {

    /**
     * @see ReactorMatchers#hasCount(long)
     */
    @Test
    public void test_hasCount() {
        assertThat(Flux.just(1, 2, 3), hasCount(3));
        assertThat(Flux.just(1, 2, 3), not(hasCount(99)));
    }

}
