package fr.pinguet62.jsfring.util.querydsl;

import java.util.function.BiFunction;

import org.primefaces.model.SortOrder;

import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.ComparableExpressionBase;
import com.mysema.query.types.expr.SimpleExpression;
import com.mysema.query.types.path.EntityPathBase;

/**
 * Convert the property and the {@link SortOrder} of an {@link EntityPathBase}
 * to the {@link OrderSpecifier} to apply.
 * <p>
 * Ordered field must be a {@link ComparableExpressionBase}.
 */
public final class OrderConverter implements BiFunction<String, SortOrder, OrderSpecifier<?>> {

    /** The meta object. */
    private final EntityPathBase<?> meta;

    /**
     * Constructor.
     *
     * @param meta The meta-object.
     */
    public OrderConverter(EntityPathBase<?> meta) {
        this.meta = meta;
    }

    /**
     * Apply the order: get the {@link OrderSpecifier}.
     *
     * @param property The property to order.
     * @param order The {@link SortOrder} (from PrimeFaces).
     * @return The {@link OrderSpecifier}.<br>
     *         {@code null} if the order is {@link SortOrder#UNSORTED}.
     * @throws NullPointerException If {@code property} or {@code order} is
     *             {@code null}.
     * @throws ClassCastException The target field is not a
     *             {@link ComparableExpressionBase}, so doen't support filter.
     * @see PropertyConverter Transform property name to
     *      {@link SimpleExpression}.
     */
    @Override
    public OrderSpecifier<?> apply(String property, SortOrder order) {
        // Attribute
        SimpleExpression<?> attribute = new PropertyConverter(meta).apply(property);
        ComparableExpressionBase<?> comparableAttribute = (ComparableExpressionBase<?>) attribute;

        // Apply
        switch (order) {
            case UNSORTED:
                return null;
            case ASCENDING:
                return comparableAttribute.asc();
            case DESCENDING:
                return comparableAttribute.desc();
            default:
                throw new UnsupportedOperationException("Unknow sort: " + order);
        }
    }

}