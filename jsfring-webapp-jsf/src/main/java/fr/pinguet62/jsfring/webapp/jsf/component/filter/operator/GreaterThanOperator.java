package fr.pinguet62.jsfring.webapp.jsf.component.filter.operator;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.NumberExpression;

public final class GreaterThanOperator<T extends Number & Comparable<?>> implements NumberOperator<T> {

    private static final long serialVersionUID = 1;

    @Override
    public Predicate apply(NumberExpression<T> path, T arg1, T arg2) {
        if (arg1 == null)
            return new BooleanBuilder();
        return path.gt(arg1);
    }

    @Override
    public boolean equals(Object obj) {
        return equalsUtil(obj);
    }

    @Override
    public String getMessage() {
        return "greater than";
    }

    @Override
    public int getNumberOfParameters() {
        return 1;
    }

    @Override
    public int hashCode() {
        return hashCodeUtil();
    }

}