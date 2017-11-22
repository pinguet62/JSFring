package fr.pinguet62.jsfring.common.querydsl;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import fr.pinguet62.jsfring.common.reflection.PropertyResolver;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.querydsl.QSort;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.springframework.data.querydsl.QSort.unsorted;

public final class SortConverter implements BiFunction<String, Direction, QSort> {

    /**
     * The meta-object.
     */
    private final EntityPath<?> meta;

    /**
     * @param meta The meta-object.
     */
    public SortConverter(EntityPath<?> meta) {
        this.meta = meta;
    }

    /**
     * @param direction {@code null} for {@link QSort#unsorted}
     */
    @Override
    public QSort apply(String fieldName, Direction direction) {
        if (fieldName == null)
            return unsorted();

        ComparableExpressionBase<?> field = (ComparableExpressionBase<?>) new PropertyResolver(meta).apply(fieldName);
        Function<ComparableExpressionBase<?>, OrderSpecifier<?>> directionApplier = new DirectionApplierConverter().apply(direction);
        OrderSpecifier<?> orderSpecifier = directionApplier.apply(field);
        return new QSort(orderSpecifier);
    }

}
