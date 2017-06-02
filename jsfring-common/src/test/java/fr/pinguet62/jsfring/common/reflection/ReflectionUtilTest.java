package fr.pinguet62.jsfring.common.reflection;

import static fr.pinguet62.jsfring.common.querydsl.ReflectionUtils.getDefaultMetaObject;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import javax.persistence.Entity;

import org.junit.Test;

import fr.pinguet62.jsfring.common.querydsl.ReflectionUtils;
import fr.pinguet62.jsfring.util.querydsl.model.Profile;
import fr.pinguet62.jsfring.util.querydsl.model.QProfile;
import fr.pinguet62.jsfring.util.querydsl.model.QRight;
import fr.pinguet62.jsfring.util.querydsl.model.QUser;
import fr.pinguet62.jsfring.util.querydsl.model.Right;
import fr.pinguet62.jsfring.util.querydsl.model.User;

/** @see ReflectionUtils */
public class ReflectionUtilTest {

    /** @see ReflectionUtils#getDefaultMetaObject(Class) */
    @Test
    public void test_getDefaultMetaObject() {
        assertThat(getDefaultMetaObject(User.class), is(equalTo(QUser.user)));
        assertThat(getDefaultMetaObject(Profile.class), is(equalTo(QProfile.profile)));
        assertThat(getDefaultMetaObject(Right.class), is(equalTo(QRight.right_)));
    }

    /**
     * Parameter not valid: {@code null}, not an {@link Entity}.
     *
     * @see ReflectionUtils#getDefaultMetaObject(Class)
     */
    @Test
    public void test_getDefaultMetaObject_badEntityType() {
        for (Class<?> type : asList(null, String.class))
            try {
                getDefaultMetaObject(type);
                fail();
            } catch (RuntimeException exception) {
            }
    }

}