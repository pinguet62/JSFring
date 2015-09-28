package fr.pinguet62.jsfring.gui.component.filter.operator;

import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.SimpleExpression;

/**
 * This implementation requires {@link SimpleExpression path} type, because it
 * can be applied on several types.
 */
public final class EqualsToOperator<Exp extends SimpleExpression<T>, T> implements Operator<Exp, T> {

    private static final long serialVersionUID = 1;

    @Override
    public Predicate apply(Exp path, T arg1, T arg2) {
        return path.eq(arg1);
    }

    @Override
    public boolean equals(Object obj) {
        return equalsUtil(obj);
    }

    @Override
    public String getMessage() {
        return "equals to";
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