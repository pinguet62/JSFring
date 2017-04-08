package fr.pinguet62.jsfring.gui.component.filter.operator;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.SimpleExpression;

public final class IsNullOperator<Exp extends SimpleExpression<T>, T> implements Operator<Exp, T> {

    private static final long serialVersionUID = 1;

    @Override
    public Predicate apply(Exp path, T arg1, T arg2) {
        return path.isNull();
    }

    @Override
    public boolean equals(Object obj) {
        return equalsUtil(obj);
    }

    @Override
    public String getMessage() {
        return "is null";
    }

    @Override
    public int getNumberOfParameters() {
        return 0;
    }

    @Override
    public int hashCode() {
        return hashCodeUtil();
    }

}