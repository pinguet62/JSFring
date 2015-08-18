package fr.pinguet62.jsfring.gui.util.querydsl.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import fr.pinguet62.jsfring.model.sample.QDescription;

/** @see PropertyConverter */
public final class PropertyConverterTest {

    /** @see PropertyConverter#getAttribute() */
    @Test
    public void test_getAttribute() {
        QDescription meta = QDescription.description;
        PropertyConverter property = new PropertyConverter(meta);
        assertEquals(meta.language.code, property.apply("language.code"));
    }

    /** @see PropertyConverter#getAttribute() */
    @Test
    public void test_getAttribute_propertyNotFound() {
        QDescription meta = QDescription.description;
        PropertyConverter property = new PropertyConverter(meta);

        for (String strProperty : Arrays.asList("", "toto", "language.toto"))
            try {
                property.apply(strProperty);
                fail();
            } catch (IllegalArgumentException exception) {
            }
    }

}
