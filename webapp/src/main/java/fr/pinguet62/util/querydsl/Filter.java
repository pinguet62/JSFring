package fr.pinguet62.util.querydsl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.ComparableExpressionBase;
import com.mysema.query.types.expr.NumberExpression;
import com.mysema.query.types.expr.SimpleExpression;
import com.mysema.query.types.path.EntityPathBase;

import fr.pinguet62.dictionary.model.QProfile;

/**
 * Apply the filters on fields. <br/>
 * Use the {@code like} expression.
 */
public final class Filter implements
        Function<Map<String, Object>, BooleanExpression> {

    /** Error during {@link FilterTest} application. */
    public static class FilterException extends RuntimeException {
        /** Serial version UID. */
        private static final long serialVersionUID = 1;

        public FilterException(String message) {
            super(message);
        }

        public FilterException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Apply the {@code like} expression on the {@link SimpleExpression}.<br/>
     * If the value is a number ({@link NumberExpression}), the filter will
     * apply {@code like} on its {@link String} value.
     *
     * @param attribute
     *            The {@link SimpleExpression}.
     * @param value
     *            The filter value. The {@link String#toString()} method is
     *            applied.
     * @return The {@link BooleanExpression}.
     * @throws UnsupportedOperationException
     *             Bad {@link SimpleExpression} type.
     */
    static BooleanExpression applyLike(SimpleExpression<?> attribute,
            Object value) {
        if (attribute instanceof ComparableExpressionBase) {
            ComparableExpressionBase<?> comparableExpression = (ComparableExpressionBase<?>) attribute;
            return comparableExpression.stringValue()
                    .contains(value.toString());
        } else
            throw new UnsupportedOperationException("The attribute "
                    + attribute + " doesn't not support \"like\" condition.");
    }

    /**
     * The meta object.<br/>
     * For example {@link QProfile#profile}.
     */
    private final EntityPathBase<?> meta;

    /**
     * Constructor.
     *
     * @param meta
     *            The The meta object.
     */
    public Filter(EntityPathBase<?> meta) {
        this.meta = meta;
    }

    /**
     * @param filters
     *            The properties and the values.
     * @return The {@link BooleanExpression}.
     * @throws UnsupportedOperationException
     *             The target field doen't exist or doen't support filter.
     */
    @Override
    public BooleanExpression apply(Map<String, Object> filters) {
        BooleanExpression condition = null;
        for (Entry<String, Object> filter : filters.entrySet()) {
            // Attribute
            String property = filter.getKey();
            SimpleExpression<?> attribute = new Property(meta)
                    .getAttribute(property);
            // Condition
            Object value = filter.getValue();
            condition = applyLike(attribute, value);
            // Apply
            condition = condition.and(condition);
        }
        return condition;
    }

}
