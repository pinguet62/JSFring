package fr.pinguet62.jsfring.gui.util.querydsl.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import fr.pinguet62.jsfring.model.QProfile;
import fr.pinguet62.jsfring.model.QRight;
import fr.pinguet62.jsfring.model.QUser;

/** @see PropertyConverter */
public final class PropertyConverterTest {

    /** @see PropertyConverter#apply(String) */
    @Test
    public void test_apply_propertyNotFound() {
        QRight right = QRight.right_;

        assertNotNull(new PropertyConverter(right).apply("code"));

        PropertyConverter converter = new PropertyConverter(right);
        for (String property : Arrays.asList("", " ", " code", "code ", " code ", ".", ".code", "code.", ".code.", "code..foo"))
            try {
                converter.apply(property);
                fail(property);
            } catch (IllegalArgumentException exception) {}
    }

    /**
     * The target field-type is invalid.
     *
     * @see PropertyConverter#apply(String)
     * @see ClassCastException
     */
    @Test
    public void test_apply_targetAttributeBadType() {
        try {
            assertEquals(QRight.right_.profiles, new PropertyConverter(QRight.right_).apply("profiles"));
            fail();
        } catch (ClassCastException e) {}

        try {
            assertEquals(QProfile.profile.rights, new PropertyConverter(QProfile.profile).apply("rights"));
            fail();
        } catch (ClassCastException e) {}
        try {
            assertEquals(QProfile.profile.users, new PropertyConverter(QProfile.profile).apply("users"));
            fail();
        } catch (ClassCastException e) {}

        try {
            assertEquals(QUser.user.profiles, new PropertyConverter(QUser.user).apply("profiles"));
            fail();
        } catch (ClassCastException e) {}
    }

    /** @see PropertyConverter#apply(String) */
    @Test
    public void test_appy() {
        {
            QRight right = QRight.right_;
            assertEquals(right.code, new PropertyConverter(right).apply("code"));
            assertEquals(right.title, new PropertyConverter(right).apply("title"));
        }
        {
            QProfile profile = QProfile.profile;
            assertEquals(profile.id, new PropertyConverter(profile).apply("id"));
            assertEquals(profile.title, new PropertyConverter(profile).apply("title"));
        }
        {
            QUser user = QUser.user;
            assertEquals(user.login, new PropertyConverter(user).apply("login"));
            assertEquals(user.password, new PropertyConverter(user).apply("password"));
            assertEquals(user.email, new PropertyConverter(user).apply("email"));
            assertEquals(user.lastConnection, new PropertyConverter(user).apply("lastConnection"));
        }
    }

    /**
     * Navigate to other relations by calling different properties.
     *
     * @see PropertyConverter#apply(String)
     */
    @Test
    public void test_appy_manyCall() {
        throw new UnsupportedOperationException("Not implemented");
    }

}