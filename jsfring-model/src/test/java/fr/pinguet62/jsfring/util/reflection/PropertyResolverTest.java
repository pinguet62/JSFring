package fr.pinguet62.jsfring.util.reflection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import fr.pinguet62.jsfring.model.QProfile;
import fr.pinguet62.jsfring.model.QRight;
import fr.pinguet62.jsfring.model.QUser;

/** @see PropertyResolver */
public final class PropertyResolverTest {

    /** @see PropertyResolver#apply(String) */
    @Test
    public void test_apply_propertyNotFound() {
        QRight right = QRight.right_;

        assertNotNull(new PropertyResolver(right).apply("code"));

        PropertyResolver converter = new PropertyResolver(right);
        for (String property : Arrays.asList("", " ", " code", "code ", " code ", ".", ".code", "code.", ".code.", "code..foo"))
            try {
                converter.apply(property);
                fail(property);
            } catch (IllegalArgumentException exception) {}
    }

    /**
     * The target field-type is invalid.
     *
     * @see PropertyResolver#apply(String)
     * @see ClassCastException
     */
    @Test
    public void test_apply_targetAttributeBadType() {
        try {
            assertEquals(QRight.right_.profiles, new PropertyResolver(QRight.right_).apply("profiles"));
            fail();
        } catch (ClassCastException e) {}

        try {
            assertEquals(QProfile.profile.rights, new PropertyResolver(QProfile.profile).apply("rights"));
            fail();
        } catch (ClassCastException e) {}
        try {
            assertEquals(QProfile.profile.users, new PropertyResolver(QProfile.profile).apply("users"));
            fail();
        } catch (ClassCastException e) {}

        try {
            assertEquals(QUser.user.profiles, new PropertyResolver(QUser.user).apply("profiles"));
            fail();
        } catch (ClassCastException e) {}
    }

    /** @see PropertyResolver#apply(String) */
    @Test
    public void test_appy() {
        {
            QRight right = QRight.right_;
            assertEquals(right.code, new PropertyResolver(right).apply("code"));
            assertEquals(right.title, new PropertyResolver(right).apply("title"));
        }
        {
            QProfile profile = QProfile.profile;
            assertEquals(profile.id, new PropertyResolver(profile).apply("id"));
            assertEquals(profile.title, new PropertyResolver(profile).apply("title"));
        }
        {
            QUser user = QUser.user;
            assertEquals(user.login, new PropertyResolver(user).apply("login"));
            assertEquals(user.password, new PropertyResolver(user).apply("password"));
            assertEquals(user.email, new PropertyResolver(user).apply("email"));
            assertEquals(user.lastConnection, new PropertyResolver(user).apply("lastConnection"));
        }
    }

}