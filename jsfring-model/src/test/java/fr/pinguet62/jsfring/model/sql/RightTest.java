package fr.pinguet62.jsfring.model.sql;

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
        assertThat(new Right(), is(equalTo(new Right())));
        assertThat(new Right("same code"), is(equalTo(new Right("same code"))));
        assertThat(new Right("same code", "AAA"), is(equalTo(new Right("same code", "111"))));

        assertThat(new Right("a code"), is(not(equalTo(new Right("other value")))));
        assertThat(new Right(), is(not(equalTo("other type"))));
    }

}