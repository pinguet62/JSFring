package fr.pinguet62.jsfring.common.querydsl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.LiteralExpression;
import com.querydsl.core.types.dsl.SimpleExpression;

import fr.pinguet62.jsfring.common.reflection.PropertyResolver;

/**
 * Convert a {@link Map} who associate the property and the value, to the {@link Predicate} used to filter a {@link QueryBase
 * query}.
 * <p>
 * Filtered field must be a {@link ComparableExpressionBase}.
 * <p>
 * Default criteria is {@code like}, to test if <i>filed-value</i> contains the {@link String} <i>filter-value</i>.
 */
public final class FilterConverter implements Function<Map<String, Object>, Predicate> {

    /** The meta-object. */
    private final EntityPath<?> meta;

    /**
     * @param meta
     *            The meta-object.
     */
    public FilterConverter(EntityPath<?> meta) {
        this.meta = meta;
    }

    /**
     * Apply the filter: get the {@link Predicate}.
     *
     * @param filters
     *            The association of property/value.
     * @return The {@link Predicate} built with different filters.
     * @throws NullPointerException
     *             If {@code filters} is {@code null}.
     * @throws ClassCastException
     *             The target field is not a {@link ComparableExpressionBase}, so doen't support filter.
     * @see PropertyResolver Transform property name to {@link SimpleExpression} .
     */
    @Override
    public Predicate apply(Map<String, Object> filters) {
        PropertyResolver propertyConverter = new PropertyResolver(meta);
        BooleanBuilder builder = new BooleanBuilder();
        for (Entry<String, Object> filter : filters.entrySet()) {
            // Field
            String property = filter.getKey();
            SimpleExpression<?> attribute = (SimpleExpression<?>) propertyConverter.apply(property);
            LiteralExpression<?> literal = (LiteralExpression<?>) attribute;

            // Value
            String value = filter.getValue().toString();

            // Apply
            BooleanExpression criteria = literal.stringValue().contains(value);
            builder.and(criteria);
        }
        return builder;
    }

}