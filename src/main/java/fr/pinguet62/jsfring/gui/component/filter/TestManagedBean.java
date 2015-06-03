package fr.pinguet62.jsfring.gui.component.filter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.jsfring.gui.AbstractManagedBean;
import fr.pinguet62.jsfring.model.QUser;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.service.AbstractService;

@ManagedBean
@ViewScoped
public class TestManagedBean extends AbstractManagedBean<User> {

    private static final long serialVersionUID = 1;

    private NumberPathFilter<Integer> numberFilter = new NumberPathFilter<Integer>(
            QUser.user.login.length());

    private NumberPathFilter<Integer> numberFilter2 = new NumberPathFilter<Integer>(
            QUser.user.login.length());

    private StringPathFilter stringFilter = new StringPathFilter(
            QUser.user.login);

    private StringPathFilter stringFilter2 = new StringPathFilter(
            QUser.user.login);

    public NumberPathFilter<Integer> getNumberFilter() {
        System.out.println("Getter");
        return numberFilter;
    }

    public NumberPathFilter<Integer> getNumberFilter2() {
        return numberFilter2;
    }

    @Override
    protected JPAQuery getQuery() {
        return new JPAQuery().from(QUser.user)
                // .where(stringFilter.get())
                // .where(stringFilter2.get())
                .where(numberFilter.get())
                // .where(numberFilter2.get())
                ;
    }

    @Override
    public AbstractService<User, ?> getService() {
        return null;
    }

    public StringPathFilter getStringFilter() {
        return stringFilter;
    }

    public StringPathFilter getStringFilter2() {
        return stringFilter2;
    }

    public void setNumberFilter(NumberPathFilter<Integer> numberFilter) {
        this.numberFilter = numberFilter;
    }

    public void setNumberFilter2(NumberPathFilter<Integer> numberFilter2) {
        this.numberFilter2 = numberFilter2;
    }

    public void setStringFilter(StringPathFilter filter) {
        stringFilter = filter;
    }

    public void setStringFilter2(StringPathFilter stringFilter2) {
        this.stringFilter2 = stringFilter2;
    }

    public void validate() {
        System.out.println(getQuery());
    }

}