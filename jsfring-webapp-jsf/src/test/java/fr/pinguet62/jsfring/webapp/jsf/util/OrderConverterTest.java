package fr.pinguet62.jsfring.webapp.jsf.util;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.primefaces.model.SortOrder.ASCENDING;
import static org.primefaces.model.SortOrder.DESCENDING;
import static org.primefaces.model.SortOrder.UNSORTED;

import java.util.function.Function;

import org.junit.Test;
import org.primefaces.model.SortOrder;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;

import fr.pinguet62.jsfring.model.sql.QProfile;
import fr.pinguet62.jsfring.model.sql.QRight;
import fr.pinguet62.jsfring.model.sql.QUser;
import fr.pinguet62.jsfring.webapp.jsf.util.OrderConverter;

/** @see OrderConverter */
public final class OrderConverterTest {

    /** @see OrderConverter#apply(SortOrder) */
    @Test
    public void test_apply() {
        Function<ComparableExpressionBase<?>, OrderSpecifier<?>> ascOrderApplier = new OrderConverter().apply(ASCENDING);
        Function<ComparableExpressionBase<?>, OrderSpecifier<?>> descOrderApplier = new OrderConverter().apply(DESCENDING);

        {
            QRight right = QRight.right_;

            assertThat(ascOrderApplier.apply(right.code), is(equalTo(right.code.asc())));
            assertThat(descOrderApplier.apply(right.code), is(equalTo(right.code.desc())));

            assertThat(ascOrderApplier.apply(right.title), is(equalTo(right.title.asc())));
            assertThat(descOrderApplier.apply(right.title), is(equalTo(right.title.desc())));
        }
        {
            QProfile profile = QProfile.profile;

            assertThat(ascOrderApplier.apply(profile.id), is(equalTo(profile.id.asc())));
            assertThat(descOrderApplier.apply(profile.id), is(equalTo(profile.id.desc())));
        }
        {
            QUser user = QUser.user;

            assertThat(ascOrderApplier.apply(user.email), is(equalTo(user.email.asc())));
            assertThat(descOrderApplier.apply(user.email), is(equalTo(user.email.desc())));
        }
    }

    /**
     * The function must be null.
     *
     * @see OrderConverter#apply(SortOrder)
     * @see SortOrder#UNSORTED
     */
    @Test
    public void test_apply_unsorded() {
        assertThat(new OrderConverter().apply(UNSORTED), is(nullValue()));
    }

}