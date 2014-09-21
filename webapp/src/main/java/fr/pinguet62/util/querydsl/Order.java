package fr.pinguet62.util.querydsl;

import java.util.function.BiFunction;

import org.primefaces.model.SortOrder;

import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.ComparableExpressionBase;
import com.mysema.query.types.expr.SimpleExpression;
import com.mysema.query.types.path.EntityPathBase;

import fr.pinguet62.dictionary.model.QProfile;

public final class Order implements
        BiFunction<String, SortOrder, OrderSpecifier<?>> {

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
    public Order(EntityPathBase<?> meta) {
        this.meta = meta;
    }

    /**
     * Get the applied {@link OrderSpecifier}.
     *
     * @param property
     *            The property.
     * @param primeOrder
     *            The {@link SortOrder} of PrimeFaces.
     * @return The {@link OrderSpecifier}.
     *
     * @UnsupportedOperationException The field doen't support ordering.
     */
    @Override
    public OrderSpecifier<?> apply(String property, SortOrder primeOrder) {
        // Attribute
        SimpleExpression<?> attribute = new Property(meta)
                .getAttribute(property);
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
