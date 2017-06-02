package fr.pinguet62.jsfring.model.sql;

import static fr.pinguet62.jsfring.model.sql.Right.builder;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/** @see Right */
public final class RightTest {

    /** @see Right#equals(Object) */
    @Test
    public void test_equals() {
        assertThat(builder().build(), is(equalTo(builder().build())));
        assertThat(builder().code("same code").build(), is(equalTo(builder().code("same code").build())));
        assertThat(builder().code("same code").title("AAA").build(),
                is(equalTo(builder().code("same code").title("111").build())));

        assertThat(builder().code("a code").build(), is(not(equalTo(builder().code("other value").build()))));
        assertThat(builder().build(), is(not(equalTo("other type"))));
    }

}