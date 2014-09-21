package fr.pinguet62.util.querydsl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import fr.pinguet62.dictionary.model.QDescription;

/** @see Property */
public final class PropertyTest {

    /** @see Property#getAttribute() */
    @Test
    public void test_getAttribute() {
        QDescription meta = QDescription.description;
        Property property = new Property(meta);
        assertEquals(meta.language.code, property.getAttribute("language.code"));
    }

    /** @see Property#getAttribute() */
    @Test
    public void test_getAttribute_propertyNotFound() {
        QDescription meta = QDescription.description;
        Property property = new Property(meta);

        for (String strProperty : Arrays.asList("", "toto", "language.toto"))
            try {
                property.getAttribute(strProperty);
                fail();
            } catch (IllegalArgumentException exception) {
            }
    }

}
