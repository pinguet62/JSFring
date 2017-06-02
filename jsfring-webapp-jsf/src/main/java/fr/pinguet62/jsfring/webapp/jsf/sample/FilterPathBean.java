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
import lombok.Getter;
import lombok.Setter;

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
    @Getter
    @Setter
    private NumberPathFilter<Integer> numberFilterDefault = new NumberPathFilter<Integer>(EXPRESSION_NUMBER);

    /**
     * @see User#email
     * @see StringExpression#length()
     * @see LongRangeValidator
     */
    @Getter
    @Setter
    private NumberPathFilter<Integer> numberFilterLongRange = new NumberPathFilter<Integer>(EXPRESSION_NUMBER);

    /** @see User#email */
    @Getter
    @Setter
    private StringPathFilter stringFilterDefault = new StringPathFilter(EXPRESSION_STRING);

    /**
     * @see User#email
     * @see RegexValidator
     */
    @Getter
    @Setter
    private StringPathFilter stringFilterRegex = new StringPathFilter(EXPRESSION_STRING);

}