package fr.pinguet62.util.querydsl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.primefaces.model.SortOrder.ASCENDING;
import static org.primefaces.model.SortOrder.DESCENDING;

import java.util.Arrays;

import org.junit.Test;
import org.primefaces.model.SortOrder;

import com.mysema.query.types.OrderSpecifier;

import fr.pinguet62.jsfring.model.QProfile;
import fr.pinguet62.jsfring.model.QRight;
import fr.pinguet62.jsfring.model.QUser;
import fr.pinguet62.jsfring.util.querydsl.OrderConverter;

/** @see OrderConverter */
public final class OrderConverterTest {

    /** @see OrderConverter#apply(String, SortOrder) */
    @Test
    public void test_apply() {
        {
            QRight right = QRight.right_;
            assertEquals(right.code.asc(), new OrderConverter(right).apply(right.code.toString(), ASCENDING));
            assertEquals(right.code.desc(), new OrderConverter(right).apply(right.code.toString(), DESCENDING));
        }
        {
            QProfile profile = QProfile.profile;
            assertEquals(profile.id.asc(), new OrderConverter(profile).apply(profile.id.toString(), ASCENDING));
            assertEquals(profile.id.desc(), new OrderConverter(profile).apply(profile.id.toString(), DESCENDING));
        }
        {
            QUser user = QUser.user;
            assertEquals(user.login.asc(), new OrderConverter(user).apply(user.login.toString(), ASCENDING));
            assertEquals(user.login.desc(), new OrderConverter(user).apply(user.login.toString(), DESCENDING));
        }
    }

    /**
     * @see OrderConverter#apply(String, SortOrder)
     * @see IllegalArgumentException
     */
    @Test
    public void test_apply_invalidProperty() {
        QRight right = QRight.right_;
        for (String property : Arrays.asList("", ".", "foo"))
            try {
                new OrderConverter(right).apply(property, SortOrder.ASCENDING);
                fail();
            } catch (IllegalArgumentException e) {}
    }

    /**
     * The {@link OrderSpecifier} must be null.
     *
     * @see OrderConverter#apply(String, SortOrder)
     * @see SortOrder#UNSORTED
     */
    @Test
    public void test_apply_unsorded() {
        {
            QRight right = QRight.right_;
            assertNull(new OrderConverter(right).apply(right.code.toString(), SortOrder.UNSORTED));
            assertNull(new OrderConverter(right).apply(right.title.toString(), SortOrder.UNSORTED));
        }
        {
            QProfile profile = QProfile.profile;
            assertNull(new OrderConverter(profile).apply(profile.id.toString(), SortOrder.UNSORTED));
            assertNull(new OrderConverter(profile).apply(profile.title.toString(), SortOrder.UNSORTED));
        }
        {
            QUser user = QUser.user;
            assertNull(new OrderConverter(user).apply(user.active.toString(), SortOrder.UNSORTED));
            assertNull(new OrderConverter(user).apply(user.email.toString(), SortOrder.UNSORTED));
            assertNull(new OrderConverter(user).apply(user.lastConnection.toString(), SortOrder.UNSORTED));
            assertNull(new OrderConverter(user).apply(user.login.toString(), SortOrder.UNSORTED));
            assertNull(new OrderConverter(user).apply(user.password.toString(), SortOrder.UNSORTED));
        }
    }

    /**
     * Field cannot be sorted.
     *
     * @see OrderConverter#apply(String, SortOrder)
     */
    @Test
    public void test_apply_unsupportedField() {
        {
            QRight right = QRight.right_;
            try {
                new OrderConverter(right).apply(right.profiles.toString(), SortOrder.ASCENDING);
                fail();
            } catch (ClassCastException e) {}
        }
        {
            QProfile profile = QProfile.profile;
            try {
                new OrderConverter(profile).apply(profile.rights.toString(), SortOrder.ASCENDING);
                fail();
            } catch (ClassCastException e) {}
            try {
                new OrderConverter(profile).apply(profile.users.toString(), SortOrder.ASCENDING);
                fail();
            } catch (ClassCastException e) {}
        }
        {
            QUser user = QUser.user;
            try {
                new OrderConverter(user).apply(user.profiles.toString(), SortOrder.ASCENDING);
                fail();
            } catch (ClassCastException e) {}
        }
    }

}