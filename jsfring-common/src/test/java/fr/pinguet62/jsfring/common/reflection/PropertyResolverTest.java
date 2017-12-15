package fr.pinguet62.jsfring.common.reflection;

import fr.pinguet62.jsfring.util.querydsl.model.QProfile;
import fr.pinguet62.jsfring.util.querydsl.model.QRight;
import fr.pinguet62.jsfring.util.querydsl.model.QUser;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @see PropertyResolver
 * @see PropertyResolver#apply(String)
 */
public class PropertyResolverTest {

    /**
     * An {@link IllegalArgumentException} must be thrown because the attribute doesn't exist.
     */
    @Test
    public void test_apply_propertyNotFound() {
        QRight right = QRight.right_;

        PropertyResolver converter = new PropertyResolver(right);
        for (String property : asList("", " ", " code", "code ", " code ", ".", ".code", "code.", ".code.", "code..foo"))
            try {
                converter.apply(property);
                fail(property);
            } catch (IllegalArgumentException exception) {
            }
    }

    /**
     * Multi-property.
     */
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

        assertThat(new PropertyResolver(foo).apply("bar.attr"), is(equalTo(foo.bar.attr)));
    }

    /**
     * Meta-model for QueryDsl.
     */
    @Test
    public void test_appy_QModel() {
        {
            QRight right = QRight.right_;
            assertThat(new PropertyResolver(right).apply("code"), is(equalTo(right.code)));
            assertThat(new PropertyResolver(right).apply("title"), is(equalTo(right.title)));
        }
        {
            QProfile profile = QProfile.profile;
            assertThat(new PropertyResolver(profile).apply("id"), is(equalTo(profile.id)));
            assertThat(new PropertyResolver(profile).apply("title"), is(equalTo(profile.title)));
        }
        {
            QUser user = QUser.user;
            assertThat(new PropertyResolver(user).apply(user.email.toString()), is(equalTo(user.email)));
            assertThat(new PropertyResolver(user).apply(user.password.toString()), is(equalTo(user.password)));
            assertThat(new PropertyResolver(user).apply(user.lastConnection.toString()), is(equalTo(user.lastConnection)));
        }
    }

}