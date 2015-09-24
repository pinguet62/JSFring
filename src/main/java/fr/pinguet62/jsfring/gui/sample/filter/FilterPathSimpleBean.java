package fr.pinguet62.jsfring.gui.sample.filter;

import java.io.Serializable;

import javax.inject.Named;

import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.jsfring.gui.component.filter.NumberPathFilter;
import fr.pinguet62.jsfring.gui.component.filter.PathFilter;
import fr.pinguet62.jsfring.gui.component.filter.StringPathFilter;
import fr.pinguet62.jsfring.model.QUser;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.util.cdi.scope.SpringViewScoped;

@Named
@SpringViewScoped
public final class FilterPathSimpleBean implements Serializable {

    private static final long serialVersionUID = 1;

    /** @see User#email */
    private NumberPathFilter<Integer> numberFilter = new NumberPathFilter<Integer>(QUser.user.email.length());

    private String sql = "";

    /** @see User#login */
    private StringPathFilter stringFilter = new StringPathFilter(QUser.user.login);

    public NumberPathFilter<Integer> getNumberFilter() {
        return numberFilter;
    }

    public String getNumberFilterPredicate() {
        return numberFilter.isValid() ? numberFilter.get().toString() : "";
    }

    public String getSQL() {
        return sql;
    }

    public StringPathFilter getStringFilter() {
        return stringFilter;
    }

    public String getStringFilterPredicate() {
        return stringFilter.isValid() ? stringFilter.get().toString() : "";
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