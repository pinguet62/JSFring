package fr.pinguet62.util.querydsl.converter;

import java.util.function.BiFunction;

import org.primefaces.model.SortOrder;

import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.ComparableExpressionBase;
import com.mysema.query.types.expr.SimpleExpression;
import com.mysema.query.types.path.EntityPathBase;

/**
 * Convert the property and the {@link SortOrder} of an {@link EntityPathBase}
 * to the {@link OrderSpecifier} to apply.
 *
 * @see SortOrder
 */
public final class OrderConverter implements
BiFunction<String, SortOrder, OrderSpecifier<?>> {

    /** The meta object. */
    private final EntityPathBase<?> meta;

    /**
     * Constructor.
     *
     * @param meta
     *            The meta-object.
     */
    public OrderConverter(EntityPathBase<?> meta) {
        this.meta = meta;
    }

    /**
     * Apply the ordering: get the {@link OrderSpecifier}.
     *
     * @param property
     *            The property.
     * @param primeOrder
     *            The {@link SortOrder} of PrimeFaces.
     * @return The {@link OrderSpecifier}. {@code null} if the order is
     *         {@link SortOrder#UNSORTED}.
     *
     * @throws UnsupportedOperationException
     *             The field doen't support ordering.
     */
    @Override
    public OrderSpecifier<?> apply(String property, SortOrder primeOrder) {
        // Attribute
        SimpleExpression<?> attribute = new PropertyConverter(meta)
        .apply(property);
        if (!(attribute instanceof ComparableExpressionBase))
            throw new UnsupportedOperationException("The field " + attribute
                    + " doesn't support ordering.");
        ComparableExpressionBase<?> comparableAttribute = (ComparableExpressionBase<?>) attribute;

        // Apply
        switch (primeOrder) {
            case UNSORTED:
                return null;
            case ASCENDING:
                return comparableAttribute.asc();
            case DESCENDING:
                return comparableAttribute.desc();
            default:
                throw new IllegalArgumentException("Unknow sort: " + primeOrder);
        }
    }

}
