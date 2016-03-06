package fr.pinguet62.jsfring.gui.component.filter.operator;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.StringExpression;

public final class LikeOperator implements StringOperator {

    private static final long serialVersionUID = 1;

    @Override
    public Predicate apply(StringExpression path, String arg1, String arg2) {
        if (arg1 == null || arg1.isEmpty())
            return new BooleanBuilder();
        return path.like(arg1);
    }

    @Override
    public boolean equals(Object obj) {
        return equalsUtil(obj);
    }

    @Override
    public String getMessage() {
        return "like";
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