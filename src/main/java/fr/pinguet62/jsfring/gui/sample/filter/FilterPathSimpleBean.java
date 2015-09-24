package fr.pinguet62.jsfring.gui.sample.filter;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.jsfring.gui.component.filter.NumberPathFilter;
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

    public String getSQL() {
        JPAQuery query = new JPAQuery().from(QUser.user);

        if (!stringFilter.isValid())
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", null));
        else
            query.where(stringFilter.get());

        if (!numberFilter.isValid())
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", null));
        else
            query.where(numberFilter.get());

        return query.toString();
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

}