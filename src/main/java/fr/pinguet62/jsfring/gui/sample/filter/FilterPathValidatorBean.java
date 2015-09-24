package fr.pinguet62.jsfring.gui.sample.filter;

import java.io.Serializable;

import javax.faces.validator.LengthValidator;
import javax.faces.validator.LongRangeValidator;
import javax.faces.validator.RegexValidator;
import javax.inject.Named;

import org.primefaces.validate.DoubleRangeValidator;

import fr.pinguet62.jsfring.gui.component.filter.NumberPathFilter;
import fr.pinguet62.jsfring.gui.component.filter.StringPathFilter;
import fr.pinguet62.jsfring.model.QUser;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.util.cdi.scope.SpringViewScoped;

@Named
@SpringViewScoped
public final class FilterPathValidatorBean implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * @see User#email
     * @see DoubleRangeValidator
     */
    private NumberPathFilter<Integer> numberFilterDoubleRange = new NumberPathFilter<Integer>(QUser.user.email.length());

    /**
     * @see User#email
     * @see LongRangeValidator
     */
    private NumberPathFilter<Integer> numberFilterLongRange = new NumberPathFilter<Integer>(QUser.user.email.length());

    /**
     * @see User#login
     * @see LengthValidator
     */
    private StringPathFilter stringFilterLength = new StringPathFilter(QUser.user.login);

    /**
     * @see User#login
     * @see RegexValidator
     */
    private StringPathFilter stringFilterRegex = new StringPathFilter(QUser.user.login);

    public NumberPathFilter<Integer> getNumberFilterDoubleRange() {
        return numberFilterDoubleRange;
    }

    public NumberPathFilter<Integer> getNumberFilterLongRange() {
        return numberFilterLongRange;
    }

    public StringPathFilter getStringFilterLength() {
        return stringFilterLength;
    }

    public StringPathFilter getStringFilterRegex() {
        return stringFilterRegex;
    }

    public void setNumberFilterDoubleRange(NumberPathFilter<Integer> numberFilterDoubleRange) {
        this.numberFilterDoubleRange = numberFilterDoubleRange;
    }

    public void setNumberFilterLongRange(NumberPathFilter<Integer> numberFilterLongRange) {
        this.numberFilterLongRange = numberFilterLongRange;
    }

    public void setStringFilterLength(StringPathFilter stringFilterLength) {
        this.stringFilterLength = stringFilterLength;
    }

    public void setStringFilterRegex(StringPathFilter stringFilterRegex) {
        this.stringFilterRegex = stringFilterRegex;
    }

}