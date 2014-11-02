package fr.pinguet62.jsfring.gui.util.querydsl.converter;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import com.mysema.query.Query;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.ComparableExpressionBase;
import com.mysema.query.types.expr.NumberExpression;
import com.mysema.query.types.expr.SimpleExpression;
import com.mysema.query.types.path.EntityPathBase;

/**
 * Convert a {@link Map} who associate the property and the value, to the
 * {@link BooleanExpression} used to filter a {@link Query}.
 * <p>
 * Use the {@code like} criteria on field to test if it contains the
 * {@link String} value.
 */
public final class FilterConverter implements
Function<Map<String, Object>, BooleanExpression> {

    // Default visibility for Unit-Testing
    /**
     * Apply the criteria on the field.
     * <p>
     * If the value is a number ({@link NumberExpression}), the criteria will be
     * applied on its {@link String} value.
     *
     * @param attribute
     *            The {@link ComparableExpressionBase}.
     * @param value
     *            The value. The {@link String#toString()} method is applied on
     *            value.
     * @return The {@link BooleanExpression}.
     */
    static BooleanExpression applyCriteria(
            ComparableExpressionBase<?> attribute, Object value) {
        return attribute.stringValue().contains(value.toString());
    }

    /** The meta-object. */
    private final EntityPathBase<?> meta;

    /**
     * Constructor.
     *
     * @param meta
     *            The meta-object.
     */
    public FilterConverter(EntityPathBase<?> meta) {
        this.meta = meta;
    }

    /**
     * Apply the filter: get the {@link BooleanExpression}.
     *
     * @param filters
     *            The association property/value.
     * @return The {@link BooleanExpression}. {@code null} if no filter is
     *         applied.
     * @throws UnsupportedOperationException
     *             The target field doen't exist or doen't support filter.
     */
    @Override
    public BooleanExpression apply(Map<String, Object> filters) {
        BooleanExpression condition = null;
        for (Entry<String, Object> filter : filters.entrySet()) {
            // Attribute
            String property = filter.getKey();
            SimpleExpression<?> attribute = new PropertyConverter(meta)
            .apply(property);
            if (!(attribute instanceof ComparableExpressionBase))
                throw new UnsupportedOperationException("The attribute "
                        + attribute
                        + " doesn't not support \"like\" condition.");
            ComparableExpressionBase<?> comparable = (ComparableExpressionBase<?>) attribute;

            // Condition
            Object value = filter.getValue();
            BooleanExpression c = applyCriteria(comparable, value);

            // Apply
            condition = condition == null ? c : condition.and(c);
        }
        return condition;
    }

}
