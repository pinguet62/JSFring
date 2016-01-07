package fr.pinguet62.jsfring.util.reflection;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import fr.pinguet62.jsfring.model.QProfile;
import fr.pinguet62.jsfring.model.QRight;
import fr.pinguet62.jsfring.model.QUser;

/**
 * @see PropertyResolver
 * @see PropertyResolver#apply(String)
 */
public final class PropertyResolverTest {

    /**
     * An {@link IllegalArgumentException} must be thrown because the attribute
     * doesn't exist.
     */
    @Test
    public void test_apply_propertyNotFound() {
        QRight right = QRight.right_;

        PropertyResolver converter = new PropertyResolver(right);
        for (String property : asList("", " ", " code", "code ", " code ", ".", ".code", "code.", ".code.", "code..foo"))
            try {
                converter.apply(property);
                fail(property);
            } catch (IllegalArgumentException exception) {}
    }

    /** Multi-property. */
    @Test
    public void test_appy() {
        class Bar {
            int attr;
        }
        class Foo {
            Bar bar;
        }

        final int value = 5;

        Foo foo = new Foo();
        foo.bar = new Bar();
        foo.bar.attr = value;

        assertEquals(foo.bar.attr, new PropertyResolver(foo).apply("bar.attr"));
    }

    /** Meta-model for Querydsl. */
    @Test
    public void test_appy_QModel() {
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
            assertEquals(user.login, new PropertyResolver(user).apply(user.login.toString()));
            assertEquals(user.password, new PropertyResolver(user).apply(user.password.toString()));
            assertEquals(user.email, new PropertyResolver(user).apply(user.email.toString()));
            assertEquals(user.lastConnection, new PropertyResolver(user).apply(user.lastConnection.toString()));
        }
    }

}