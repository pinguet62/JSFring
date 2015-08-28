package fr.pinguet62.jsfring.gui.util.querydsl.converter;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

import fr.pinguet62.jsfring.model.QProfile;
import fr.pinguet62.jsfring.model.QRight;
import fr.pinguet62.jsfring.model.QUser;

/** @see FilterConverter */
public final class FilterConverterTest {

    /** @see FilterConverter#apply(Map) */
    @Test
    public void test_apply() {
        QUser user = QUser.user;

        // Ordered Map because the comparison of Predicate is ordered
        Map<String, Object> params = new LinkedHashMap<>();
        params.put(user.password.toString(), "ssw");
        params.put(user.email.toString(), "profile");

        FilterConverter filter = new FilterConverter(user);
        Predicate predicate = filter.apply(params);

        Predicate expected = new BooleanBuilder().and(user.password.contains("ssw").and(user.email.contains("profile")));
        assertEquals(expected, predicate);
    }

    /**
     * Apply filter on {@link Number} field.
     *
     * @see FilterConverter#apply(Map)
     */
    @Test
    public void test_apply_number() {
        QProfile profile = QProfile.profile;
        for (Number value : Arrays.asList(1, 0.1)) {
            Map<String, Object> params = new HashMap<>();
            params.put(profile.id.toString(), value);

            Predicate expected = new FilterConverter(profile).apply(params);

            Predicate actual = profile.id.stringValue().contains(value.toString());
            // toString() because Predicate::equals() check class-type
            assertEquals(actual.toString(), expected.toString());
        }
    }

    /**
     * The {@link Map} parameter is empty: no filter to apply.
     *
     * @see FilterConverter#apply(Map)
     */
    @Test
    public void test_apply_parameter_empty() {
        QUser user = QUser.user;

        Map<String, Object> params = new HashMap<>();

        FilterConverter filter = new FilterConverter(user);
        Predicate predicate = filter.apply(params);

        Predicate noFilter = new BooleanBuilder();
        assertEquals(noFilter, predicate);
    }

    /**
     * @see FilterConverter#apply(Map)
     * @see NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void test_apply_parameter_null() {
        FilterConverter filter = new FilterConverter(QUser.user);
        filter.apply(null);
    }

    /**
     * Apply filter on {@link String} field.
     *
     * @see FilterConverter#apply(Map)
     */
    @Test
    public void test_apply_string() {
        QRight right = QRight.right;
        for (String value : Arrays.asList("RIGHT_", "ILE_", "_RO", "%", "^", "\\")) {
            Map<String, Object> params = new HashMap<>();
            params.put(right.code.toString(), value);

            Predicate expected = new FilterConverter(right).apply(params);

            Predicate actual = right.code.stringValue().contains(value);
            // toString() because Predicate::equals() check class-type
            assertEquals(expected.toString(), actual.toString());
        }
    }

}