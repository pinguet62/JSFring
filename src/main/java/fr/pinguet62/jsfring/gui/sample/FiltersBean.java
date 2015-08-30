package fr.pinguet62.jsfring.gui.sample;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.inject.Named;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.StringExpression;

import fr.pinguet62.jsfring.gui.component.filter.NumberPathFilter;
import fr.pinguet62.jsfring.gui.component.filter.PathFilter;
import fr.pinguet62.jsfring.gui.component.filter.StringPathFilter;
import fr.pinguet62.jsfring.gui.component.filter.operator.ContainsOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.EqualsToOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.IsNullOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.Operator;
import fr.pinguet62.jsfring.gui.component.filter.operator.StartsWithOperator;
import fr.pinguet62.jsfring.model.QUser;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.util.cdi.scope.SpringViewScoped;

@Named
@SpringViewScoped
public final class FiltersBean implements Serializable {

    private static final long serialVersionUID = 1;

    /** @see User#email */
    private NumberPathFilter<Integer> numberFilter = new NumberPathFilter<Integer>(QUser.user.email.length());

    private String sql = "";

    /** @see User#login */
    private StringPathFilter stringFilter = new StringPathFilter(QUser.user.login) {
        @Override
        public List<Operator<StringExpression, String>> getOperators() {
            return Arrays.asList(new IsNullOperator<>(), new EqualsToOperator<StringExpression, String>(),
                    new StartsWithOperator(), new ContainsOperator());
        };
    };

    public NumberPathFilter<Integer> getNumberFilter() {
        return numberFilter;
    }

    public String getSQL() {
        return sql;
    }

    public StringPathFilter getStringFilter() {
        return stringFilter;
    }

    public void setNumberFilter(NumberPathFilter<Integer> numberFilter) {
        this.numberFilter = numberFilter;
    }

    public void setStringFilter(StringPathFilter stringFilter) {
        this.stringFilter = stringFilter;
    }

    /** Build {@link JPAQuery} from several {@link PathFilter}s. */
    public void updateSQL() {
        JPAQuery query = new JPAQuery().from(QUser.user);
        query.where(stringFilter.get()).where(numberFilter.get());
        sql = query.toString();
    }

}