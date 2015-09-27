package fr.pinguet62.jsfring.gui.component.filter.operator;

import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.StringExpression;

public final class StartsWithOperator implements StringOperator {

    private static final long serialVersionUID = 1;

    @Override
    public Predicate apply(StringExpression path, String arg1, String arg2) {
        return path.startsWith(arg1);
    }

    @Override
    public boolean equals(Object obj) {
        return equalsUtil(obj);
    }

    @Override
    public String getMessage() {
        return "starts with";
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