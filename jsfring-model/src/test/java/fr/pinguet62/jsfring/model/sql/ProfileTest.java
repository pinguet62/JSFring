package fr.pinguet62.jsfring.model.sql;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/** @see Profile */
public final class ProfileTest {

    /** @see Profile#equals(Object) */
    @Test
    public void test_equals() {
        assertThat(new Profile(), is(equalTo(new Profile())));
        assertThat(new Profile(42), is(equalTo(new Profile(42))));
        assertThat(new Profile(42, "a title"), is(equalTo(new Profile(42, "other value"))));

        assertThat(new Profile(1), is(not(equalTo(new Profile(2)))));
        assertThat(new Profile(), is(not(equalTo("other type"))));
        assertThat("other type", is(not(equalTo(new Profile()))));
    }

}