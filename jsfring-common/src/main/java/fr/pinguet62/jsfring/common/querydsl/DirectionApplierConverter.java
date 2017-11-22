package fr.pinguet62.jsfring.common.querydsl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import org.springframework.data.domain.Sort.Direction;

import java.util.function.Function;

public final class DirectionApplierConverter implements Function<Direction, Function<ComparableExpressionBase<?>, OrderSpecifier<?>>> {

    @Override
    public Function<ComparableExpressionBase<?>, OrderSpecifier<?>> apply(Direction direction) {
        switch (direction) {
            case ASC:
                return ComparableExpressionBase::asc;
            case DESC:
                return ComparableExpressionBase::desc;
            default:
                throw new UnsupportedOperationException("Unknown direction: " + direction);
        }
    }

}
