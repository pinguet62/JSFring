package fr.pinguet62.jsfring.common.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import fr.pinguet62.jsfring.util.querydsl.model.QProfile;
import fr.pinguet62.jsfring.util.querydsl.model.QRight;
import fr.pinguet62.jsfring.util.querydsl.model.QUser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @see FilterConverter
 */
public final class FilterConverterTest {

    /**
     * @see FilterConverter#apply(Map)
     */
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
        assertThat(expected, is(equalTo(predicate)));
    }

    /**
     * Apply filter on {@link Number} field.
     *
     * @see FilterConverter#apply(Map)
     */
    @Disabled // TODO Fix
    @Test
    public void test_apply_number() {
        QProfile profile = QProfile.profile;
        for (Number value : asList(1, 0.1)) {
            Map<String, Object> params = new HashMap<>();
            params.put(profile.id.toString(), value);

            Predicate expected = new FilterConverter(profile).apply(params);

            Predicate actual = profile.id.stringValue().contains(value.toString());
            // toString() because Predicate::equals() check class-type
            assertThat(actual.toString(), is(equalTo(expected.toString())));
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
        assertThat(noFilter, is(equalTo(predicate)));
    }

    /**
     * @see NullPointerException
     * @see FilterConverter#apply(Map)
     */
    @Test
    public void test_apply_parameter_null() {
        FilterConverter filter = new FilterConverter(QUser.user);
        assertThrows(NullPointerException.class, () -> filter.apply(null));
    }

    /**
     * Apply filter on {@link String} field.
     *
     * @see FilterConverter#apply(Map)
     */
    @Test
    public void test_apply_string() {
        QRight right = QRight.right_;
        for (String value : asList("RIGHT_", "ILE_", "_RO", "%", "^", "\\")) {
            Map<String, Object> params = new HashMap<>();
            params.put(right.code.toString(), value);

            Predicate expected = new FilterConverter(right).apply(params);

            Predicate actual = right.code.stringValue().contains(value);
            // toString() because Predicate::equals() check class-type
            assertThat(expected.toString(), is(equalTo(actual.toString())));
        }
    }

    /**
     * Field cannot be filtered.
     *
     * @see FilterConverter#apply(Map)
     */
    @Test
    public void test_apply_unsupportedField() {
        {
            QRight right = QRight.right_;
            try {
                Map<String, Object> params = new HashMap<>();
                params.put(right.profiles.toString(), "foo");
                new FilterConverter(right).apply(params);
                fail();
            } catch (ClassCastException e) {
            }
        }
        {
            QProfile profile = QProfile.profile;
            try {
                Map<String, Object> params = new HashMap<>();
                params.put(profile.rights.toString(), "foo");
                new FilterConverter(profile).apply(params);
                fail();
            } catch (ClassCastException e) {
            }
            try {
                Map<String, Object> params = new HashMap<>();
                params.put(profile.users.toString(), "foo");
                new FilterConverter(profile).apply(params);
                fail();
            } catch (ClassCastException e) {
            }
        }
        {
            QUser user = QUser.user;
            try {
                Map<String, Object> params = new HashMap<>();
                params.put(user.profiles.toString(), "foo");
                new FilterConverter(user).apply(params);
                fail();
            } catch (ClassCastException e) {
            }
        }
    }

}