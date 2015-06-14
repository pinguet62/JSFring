package fr.pinguet62.jsfring.gui.component.filter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.mysema.query.types.expr.NumberExpression;

import fr.pinguet62.jsfring.gui.component.filter.operator.BetweenOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.EqualsToOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.GreaterThanOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.Operator;

public final class NumberPathFilter<T extends Number & Comparable<?>> extends
        PathFilter<NumberExpression<T>, T> implements Serializable {

    private static final long serialVersionUID = 1;

    public NumberPathFilter(NumberExpression<T> path) {
        super(path);
    }

    /**
     * Get the list of {@link Operator}s, applicable on {@link #path}.
     * <p>
     * Override this method to adapt available filters as required.
     *
     * @return The ordered list of {@link Operator}s.
     */
    @Override
    public List<Operator<NumberExpression<T>, T>> getOperators() {
        return Arrays.asList(new EqualsToOperator<NumberExpression<T>, T>(),
                new GreaterThanOperator<T>(), new BetweenOperator<T>());
    }

}