package fr.pinguet62.jsfring.webapp.jsf.sample;

import java.io.Serializable;

import javax.faces.validator.LongRangeValidator;
import javax.faces.validator.RegexValidator;
import javax.inject.Named;

import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

import fr.pinguet62.jsfring.model.sql.QUser;
import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.webapp.jsf.component.filter.NumberPathFilter;
import fr.pinguet62.jsfring.webapp.jsf.component.filter.StringPathFilter;
import fr.pinguet62.jsfring.webapp.jsf.config.scope.SpringViewScoped;

@Named
@SpringViewScoped
public final class FilterPathBean implements Serializable {

    public static final NumberExpression<Integer> EXPRESSION_NUMBER = QUser.user.email.length();

    public static final StringPath EXPRESSION_STRING = QUser.user.email;

    private static final long serialVersionUID = 1;

    /**
     * @see User#email
     * @see StringExpression#length()
     */
    private NumberPathFilter<Integer> numberFilterDefault = new NumberPathFilter<Integer>(EXPRESSION_NUMBER);

    /**
     * @see User#email
     * @see StringExpression#length()
     * @see LongRangeValidator
     */
    private NumberPathFilter<Integer> numberFilterLongRange = new NumberPathFilter<Integer>(EXPRESSION_NUMBER);

    /** @see User#email */
    private StringPathFilter stringFilterDefault = new StringPathFilter(EXPRESSION_STRING);

    /**
     * @see User#email
     * @see RegexValidator
     */
    private StringPathFilter stringFilterRegex = new StringPathFilter(EXPRESSION_STRING);

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