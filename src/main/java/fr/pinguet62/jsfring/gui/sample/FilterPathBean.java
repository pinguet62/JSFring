package fr.pinguet62.jsfring.gui.sample;

import java.io.Serializable;

import javax.faces.validator.LongRangeValidator;
import javax.faces.validator.RegexValidator;
import javax.inject.Named;

import com.mysema.query.types.expr.StringExpression;

import fr.pinguet62.jsfring.gui.component.filter.NumberPathFilter;
import fr.pinguet62.jsfring.gui.component.filter.StringPathFilter;
import fr.pinguet62.jsfring.model.QUser;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.util.cdi.scope.SpringViewScoped;

@Named
@SpringViewScoped
public final class FilterPathBean implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * @see User#email
     * @see StringExpression#length()
     */
    private NumberPathFilter<Integer> numberFilterDefault = new NumberPathFilter<Integer>(QUser.user.email.length());

    /**
     * @see User#email
     * @see StringExpression#length()
     * @see LongRangeValidator
     */
    private NumberPathFilter<Integer> numberFilterLongRange = new NumberPathFilter<Integer>(QUser.user.email.length());

    /** @see User#login */
    private StringPathFilter stringFilterDefault = new StringPathFilter(QUser.user.login);

    /**
     * @see User#login
     * @see RegexValidator
     */
    private StringPathFilter stringFilterRegex = new StringPathFilter(QUser.user.login);

    public NumberPathFilter<Integer> getNumberFilterDefault() {
        return numberFilterDefault;
    }

    public NumberPathFilter<Integer> getNumberFilterLongRange() {
        return numberFilterLongRange;
    }

    public StringPathFilter getStringFilterDefault() {
        return stringFilterDefault;
    }

    public StringPathFilter getStringFilterRegex() {
        return stringFilterRegex;
    }

    public void setNumberFilterDefault(NumberPathFilter<Integer> numberFilterDefault) {
        this.numberFilterDefault = numberFilterDefault;
    }

    public void setNumberFilterLongRange(NumberPathFilter<Integer> numberFilterLongRange) {
        this.numberFilterLongRange = numberFilterLongRange;
    }

    public void setStringFilterDefault(StringPathFilter stringFilterDefault) {
        this.stringFilterDefault = stringFilterDefault;
    }

    public void setStringFilterRegex(StringPathFilter stringFilterRegex) {
        this.stringFilterRegex = stringFilterRegex;
    }

}