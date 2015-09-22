package fr.pinguet62.jsfring.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/** @see Profile */
public final class ProfileTest {

    /** @see Profile#equals(Object) */
    @Test
    public void test_equals() {
        assertEquals(new Profile(), new Profile());
        assertEquals(new Profile(42), new Profile(42));
        assertEquals(new Profile(42, "a title"), new Profile(42, "other value"));

        assertNotEquals(new Profile(1), new Profile(2));
        assertNotEquals(new Profile(), "other type");
    }

}