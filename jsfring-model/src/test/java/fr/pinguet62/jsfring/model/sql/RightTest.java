package fr.pinguet62.jsfring.model.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import fr.pinguet62.jsfring.model.sql.Right;

/** @see Right */
public final class RightTest {

    /** @see Right#equals(Object) */
    @Test
    public void test_equals() {
        assertEquals(new Right(), new Right());
        assertEquals(new Right("same code"), new Right("same code"));
        assertEquals(new Right("same code", "AAA"), new Right("same code", "111"));

        assertNotEquals(new Right("a code"), new Right("other value"));
        assertNotEquals(new Right(), "other type");
    }

}