package fr.pinguet62.jsfring.webapp.jsf.component.filter;

import static java.util.Arrays.asList;

import java.io.Serializable;
import java.util.List;

import com.querydsl.core.types.dsl.StringExpression;

import fr.pinguet62.jsfring.webapp.jsf.component.filter.operator.ContainsOperator;
import fr.pinguet62.jsfring.webapp.jsf.component.filter.operator.EndsWithOperator;
import fr.pinguet62.jsfring.webapp.jsf.component.filter.operator.EqualsToOperator;
import fr.pinguet62.jsfring.webapp.jsf.component.filter.operator.IsNullOperator;
import fr.pinguet62.jsfring.webapp.jsf.component.filter.operator.LikeOperator;
import fr.pinguet62.jsfring.webapp.jsf.component.filter.operator.Operator;
import fr.pinguet62.jsfring.webapp.jsf.component.filter.operator.StartsWithOperator;

/** A {@link PathFilter} for {@link StringExpression} fields. */
public class StringPathFilter extends PathFilter<StringExpression, String> implements Serializable {

    private static final long serialVersionUID = 1;

    public StringPathFilter(StringExpression path) {
        super(path);
    }

    @Override
    public List<Operator<StringExpression, String>> getOperators() {
        return asList(new IsNullOperator<>(), new EqualsToOperator<StringExpression, String>(), new StartsWithOperator(),
                new ContainsOperator(), new EndsWithOperator(), new LikeOperator());
    }

}