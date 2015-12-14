package fr.pinguet62.jsfring.gui.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.primefaces.model.SortOrder.ASCENDING;
import static org.primefaces.model.SortOrder.DESCENDING;
import static org.primefaces.model.SortOrder.UNSORTED;

import java.util.function.Function;

import org.junit.Test;
import org.primefaces.model.SortOrder;

import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.ComparableExpressionBase;

import fr.pinguet62.jsfring.model.QProfile;
import fr.pinguet62.jsfring.model.QRight;
import fr.pinguet62.jsfring.model.QUser;

/** @see OrderConverter */
public final class OrderConverterTest {

    /** @see OrderConverter#apply(SortOrder) */
    @Test
    public void test_apply() {
        Function<ComparableExpressionBase<?>, OrderSpecifier<?>> ascOrderApplier = new OrderConverter().apply(ASCENDING);
        Function<ComparableExpressionBase<?>, OrderSpecifier<?>> descOrderApplier = new OrderConverter().apply(DESCENDING);

        {
            QRight right = QRight.right_;

            assertEquals(right.code.asc(), ascOrderApplier.apply(right.code));
            assertEquals(right.code.desc(), descOrderApplier.apply(right.code));

            assertEquals(right.title.asc(), ascOrderApplier.apply(right.title));
            assertEquals(right.title.desc(), descOrderApplier.apply(right.title));
        }
        {
            QProfile profile = QProfile.profile;

            assertEquals(profile.id.asc(), ascOrderApplier.apply(profile.id));
            assertEquals(profile.id.desc(), descOrderApplier.apply(profile.id));
        }
        {
            QUser user = QUser.user;

            assertEquals(user.login.asc(), ascOrderApplier.apply(user.login));
            assertEquals(user.login.desc(), descOrderApplier.apply(user.login));
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
        assertNull(new OrderConverter().apply(UNSORTED));
        assertNull(new OrderConverter().apply(UNSORTED));
    }

}