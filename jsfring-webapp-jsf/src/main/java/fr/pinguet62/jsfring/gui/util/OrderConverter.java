package fr.pinguet62.jsfring.gui.util;

import java.util.function.Function;

import org.primefaces.model.SortOrder;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.SimpleExpression;

import fr.pinguet62.jsfring.util.reflection.PropertyResolver;

// TOSO Spring converter
/**
 * Convert the property and the {@link SortOrder} of an {@link EntityPathBase} to the {@link OrderSpecifier} to apply.
 * <p>
 * Ordered field must be a {@link ComparableExpressionBase}.
 */
public final class OrderConverter implements Function<SortOrder, Function<ComparableExpressionBase<?>, OrderSpecifier<?>>> {

    /**
     * Apply the order: get the {@link OrderSpecifier}.
     *
     * @param order
     *            The {@link SortOrder} (from PrimeFaces).
     * @return The {@link OrderSpecifier}.<br>
     *         {@code null} if the order is {@link SortOrder#UNSORTED}.
     * @throws NullPointerException
     *             If {@code property} or {@code order} is {@code null}.
     * @throws ClassCastException
     *             The target field is not a {@link ComparableExpressionBase}, so doen't support filter.
     * @see PropertyResolver Transform property name to {@link SimpleExpression} .
     */
    @Override
    public Function<ComparableExpressionBase<?>, OrderSpecifier<?>> apply(SortOrder order) {
        switch (order) {
            case UNSORTED: // "Unsorted Function is unknown in Querydsl
                return null;
            case ASCENDING:
                return x -> x.asc();
            case DESCENDING:
                return x -> x.desc();
            default:
                throw new UnsupportedOperationException("Unknow sort: " + order);
        }
    }

}