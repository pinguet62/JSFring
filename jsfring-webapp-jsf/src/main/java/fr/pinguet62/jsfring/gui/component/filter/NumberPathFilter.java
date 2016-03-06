package fr.pinguet62.jsfring.gui.component.filter;

import static java.util.Arrays.asList;

import java.io.Serializable;
import java.util.List;

import com.mysema.query.types.expr.NumberExpression;

import fr.pinguet62.jsfring.gui.component.filter.operator.BetweenOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.EqualsToOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.GreaterThanOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.IsNullOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.LessThanOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.Operator;

/** A {@link PathFilter} for {@link NumberExpression} fields. */
public class NumberPathFilter<T extends Number & Comparable<?>> extends PathFilter<NumberExpression<T>, T>
        implements Serializable {

    private static final long serialVersionUID = 1;

    public NumberPathFilter(NumberExpression<T> path) {
        super(path);
    }

    @Override
    public List<Operator<NumberExpression<T>, T>> getOperators() {
        return asList(new IsNullOperator<>(), new EqualsToOperator<NumberExpression<T>, T>(), new GreaterThanOperator<T>(),
                new LessThanOperator<T>(), new BetweenOperator<T>());
    }

}