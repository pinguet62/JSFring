package fr.pinguet62.jsfring.gui;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.jsfring.gui.component.filter.NumberPathFilter;
import fr.pinguet62.jsfring.gui.component.filter.PathFilter;
import fr.pinguet62.jsfring.gui.component.filter.StringPathFilter;
import fr.pinguet62.jsfring.model.QUser;

@ManagedBean
@ViewScoped
public final class FiltersManagedBean implements Serializable {

    private static final long serialVersionUID = 1;

    private NumberPathFilter<Integer> numberFilter = new NumberPathFilter<Integer>(
            QUser.user.email.length());

    private StringPathFilter stringFilter = new StringPathFilter(
            QUser.user.login);

    public NumberPathFilter<Integer> getNumberFilter() {
        return numberFilter;
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

    /** Build {@link JPAQuery} from {@link PathFilter}s. */
    public void submit() {
        System.out.println(new JPAQuery().from(QUser.user)
        /* .where(stringFilter.get()) */.where(numberFilter.get()));
    }

}