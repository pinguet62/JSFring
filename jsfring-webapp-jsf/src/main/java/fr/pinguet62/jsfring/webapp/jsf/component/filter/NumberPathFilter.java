package fr.pinguet62.jsfring.webapp.jsf.component.filter;

import com.querydsl.core.types.dsl.NumberExpression;
import fr.pinguet62.jsfring.webapp.jsf.component.filter.operator.*;

import java.io.Serializable;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * A {@link PathFilter} for {@link NumberExpression} fields.
 */
public class NumberPathFilter<T extends Number & Comparable<?>> extends PathFilter<NumberExpression<T>, T>
        implements Serializable {

    private static final long serialVersionUID = 1;

    public NumberPathFilter(NumberExpression<T> path) {
        super(path);
    }

    @Override
    public List<Operator<NumberExpression<T>, T>> getOperators() {
        return asList(
                new IsNullOperator<>(),
                new EqualsToOperator<NumberExpression<T>, T>(),
                new GreaterThanOperator<T>(),
                new LessThanOperator<T>(),
                new BetweenOperator<T>()
        );
    }

}