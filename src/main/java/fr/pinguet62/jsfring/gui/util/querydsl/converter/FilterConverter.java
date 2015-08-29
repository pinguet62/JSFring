package fr.pinguet62.jsfring.gui.util.querydsl.converter;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.support.QueryBase;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.ComparableExpressionBase;
import com.mysema.query.types.expr.SimpleExpression;
import com.mysema.query.types.path.EntityPathBase;

/**
 * Convert a {@link Map} who associate the property and the value, to the
 * {@link Predicate} used to filter a {@link QueryBase query}.
 * <p>
 * Filtered field must be a {@link ComparableExpressionBase}.
 * <p>
 * Default criteria is {@code like}, to test if <i>filed-value</i> contains the
 * {@link String} <i>filter-value</i>.
 */
public final class FilterConverter implements Function<Map<String, Object>, Predicate> {

    /** The meta-object. */
    private final EntityPathBase<?> meta;

    /**
     * Constructor.
     *
     * @param meta The meta-object.
     */
    public FilterConverter(EntityPathBase<?> meta) {
        this.meta = meta;
    }

    /**
     * Apply the filter: get the {@link Predicate}.
     *
     * @param filters The association of property/value.
     * @return The {@link Predicate} built with different filters.
     * @throws NullPointerException If {@code filters} is {@code null}.
     * @throws ClassCastException The target field is not a
     *             {@link ComparableExpressionBase}, so doen't support filter.
     * @see PropertyConverter Transform property name to
     *      {@link SimpleExpression}.
     */
    @Override
    public Predicate apply(Map<String, Object> filters) {
        BooleanBuilder builder = new BooleanBuilder();
        for (Entry<String, Object> filter : filters.entrySet()) {
            // Attribute
            String property = filter.getKey();
            SimpleExpression<?> attribute = new PropertyConverter(meta).apply(property);
            ComparableExpressionBase<?> comparable = (ComparableExpressionBase<?>) attribute;

            // Filter value
            String value = filter.getValue().toString();

            // Apply
            BooleanExpression criteria = comparable.stringValue().contains(value);
            builder.and(criteria);
        }
        return builder;
    }

}