package fr.pinguet62.jsfring.gui.component.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.faces.validator.RegexValidator;

import org.junit.Test;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.NumberExpression;
import com.mysema.query.types.path.StringPath;

import fr.pinguet62.jsfring.gui.component.filter.operator.BetweenOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.ContainsOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.EndsWithOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.EqualsToOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.GreaterThanOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.IsNullOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.LessThanOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.LikeOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.StartsWithOperator;
import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.filter.FilterField;
import fr.pinguet62.jsfring.gui.htmlunit.filter.FilterPathPage;
import fr.pinguet62.jsfring.gui.sample.FilterPathBean;
import fr.pinguet62.jsfring.model.QUser;

/**
 * @see PathFilter
 * @see FilterPathBean
 */
public final class FilterPathPageTest {

    private static class Case {

        public static Case forField(FilterField field) {
            return new Case(field);
        }

        private final FilterField field;

        private Case(FilterField field) {
            this.field = field;
        }

        public Case andValues(String... values) {
            for (int i = 0; i < values.length; i++)
                field.setValue(i, values[i]);
            return this;
        }

        public void mustFail() {
            field.submit();
            assertTrue(field.isError());
        }

        public void mustGenerate(Predicate result) {
            field.submit();
            assertFalse(field.isError());
            assertEquals(result.toString(), field.getQuery());
        }

        public Case theOperator(Class<?> operator) {
            field.setOperator(operator);
            return this;
        }

    }

    private static final NumberExpression<Integer> number = QUser.user.email.length();

    private static final FilterPathPage page = AbstractPage.get().gotoSampleFilterSimple();

    private static final StringPath string = QUser.user.login;

    /** @see NumberPathFilter */
    @Test
    public void test_NumberPathFilter() {
        FilterField numberDefault = page.getNumberFilterDefault();

        Case.forField(numberDefault).theOperator(null).andValues().mustGenerate(new BooleanBuilder());

        Case.forField(numberDefault).theOperator(IsNullOperator.class).andValues().mustGenerate(number.isNull());

        // validateRequired
        Case.forField(numberDefault).theOperator(EqualsToOperator.class).andValues("").mustFail();
        // converter
        Case.forField(numberDefault).theOperator(EqualsToOperator.class).andValues("foo").mustFail();
        Case.forField(numberDefault).theOperator(EqualsToOperator.class).andValues("1234").mustGenerate(number.eq(1234));

        // validateRequired
        Case.forField(numberDefault).theOperator(GreaterThanOperator.class).andValues("").mustFail();
        // converter
        Case.forField(numberDefault).theOperator(GreaterThanOperator.class).andValues("foo").mustFail();
        Case.forField(numberDefault).theOperator(GreaterThanOperator.class).andValues("1234").mustGenerate(number.gt(1234));

        // validateRequired
        Case.forField(numberDefault).theOperator(LessThanOperator.class).andValues("").mustFail();
        // converter
        Case.forField(numberDefault).theOperator(LessThanOperator.class).andValues("foo").mustFail();
        Case.forField(numberDefault).theOperator(LessThanOperator.class).andValues("1234").mustGenerate(number.lt(1234));

        // validateRequired
        Case.forField(numberDefault).theOperator(BetweenOperator.class).andValues("", "").mustFail();
        // validateRequired + converter
        Case.forField(numberDefault).theOperator(BetweenOperator.class).andValues("", "bar").mustFail();
        // validateRequired
        Case.forField(numberDefault).theOperator(BetweenOperator.class).andValues("", "12").mustFail();
        // converter + validateRequired
        Case.forField(numberDefault).theOperator(BetweenOperator.class).andValues("foo", "").mustFail();
        // validateRequired
        Case.forField(numberDefault).theOperator(BetweenOperator.class).andValues("12", "").mustFail();
        // converter
        Case.forField(numberDefault).theOperator(BetweenOperator.class).andValues("foo", "bar").mustFail();
        Case.forField(numberDefault).theOperator(BetweenOperator.class).andValues("12", "34")
                .mustGenerate(number.between(12, 34));
    }

    /** @see NumberPathFilter */
    @Test
    public void test_NumberPathFilter_LongRange() {
        FilterField numberDefault = page.getNumberFilterLongRange();

        Case.forField(numberDefault).theOperator(null).andValues().mustGenerate(new BooleanBuilder());

        Case.forField(numberDefault).theOperator(IsNullOperator.class).andValues().mustGenerate(number.isNull());

        // validateRequired
        Case.forField(numberDefault).theOperator(EqualsToOperator.class).andValues("").mustFail();
        // converter
        Case.forField(numberDefault).theOperator(EqualsToOperator.class).andValues("foo").mustFail();
        // validateLongRange
        Case.forField(numberDefault).theOperator(EqualsToOperator.class).andValues("0").mustFail();
        // validateLongRange
        Case.forField(numberDefault).theOperator(EqualsToOperator.class).andValues("999").mustFail();
        Case.forField(numberDefault).theOperator(EqualsToOperator.class).andValues("12").mustGenerate(number.eq(12));

        // validateRequired
        Case.forField(numberDefault).theOperator(GreaterThanOperator.class).andValues("").mustFail();
        // converter
        Case.forField(numberDefault).theOperator(GreaterThanOperator.class).andValues("foo").mustFail();
        // validateLongRange
        Case.forField(numberDefault).theOperator(GreaterThanOperator.class).andValues("0").mustFail();
        // validateLongRange
        Case.forField(numberDefault).theOperator(GreaterThanOperator.class).andValues("999").mustFail();
        Case.forField(numberDefault).theOperator(GreaterThanOperator.class).andValues("12").mustGenerate(number.gt(12));

        // validateRequired
        Case.forField(numberDefault).theOperator(LessThanOperator.class).andValues("").mustFail();
        // converter
        Case.forField(numberDefault).theOperator(LessThanOperator.class).andValues("foo").mustFail();
        // validateLongRange
        Case.forField(numberDefault).theOperator(LessThanOperator.class).andValues("0").mustFail();
        // validateLongRange
        Case.forField(numberDefault).theOperator(LessThanOperator.class).andValues("999").mustFail();
        Case.forField(numberDefault).theOperator(LessThanOperator.class).andValues("12").mustGenerate(number.lt(12));

        // validateRequired
        Case.forField(numberDefault).theOperator(BetweenOperator.class).andValues("", "").mustFail();
        // validateRequired + converter
        Case.forField(numberDefault).theOperator(BetweenOperator.class).andValues("", "bar").mustFail();
        // validateRequired
        Case.forField(numberDefault).theOperator(BetweenOperator.class).andValues("", "12").mustFail();
        // converter + validateRequired
        Case.forField(numberDefault).theOperator(BetweenOperator.class).andValues("foo", "").mustFail();
        // validateRequired
        Case.forField(numberDefault).theOperator(BetweenOperator.class).andValues("12", "").mustFail();
        Case.forField(numberDefault).theOperator(BetweenOperator.class).andValues("foo", "bar").mustFail();
        // validateRequired
        Case.forField(numberDefault).theOperator(BetweenOperator.class).andValues("0", "999").mustFail();
        // validateRequired
        Case.forField(numberDefault).theOperator(BetweenOperator.class).andValues("12", "999").mustFail();
        // validateRequired
        Case.forField(numberDefault).theOperator(BetweenOperator.class).andValues("0", "34").mustFail();
        Case.forField(numberDefault).theOperator(BetweenOperator.class).andValues("12", "34")
                .mustGenerate(number.between(12, 34));
    }

    /** @see StringPathFilter */
    @Test
    public void test_StringPathFilter() {
        FilterField stringDefault = page.getStringFilterDefault();

        Case.forField(stringDefault).theOperator(null).andValues().mustGenerate(new BooleanBuilder());

        Case.forField(stringDefault).theOperator(IsNullOperator.class).andValues().mustGenerate(string.isNull());

        Case.forField(stringDefault).theOperator(EqualsToOperator.class).andValues("").mustGenerate(string.eq(""));
        Case.forField(stringDefault).theOperator(EqualsToOperator.class).andValues("foo").mustGenerate(string.eq("foo"));

        Case.forField(stringDefault).theOperator(StartsWithOperator.class).andValues("").mustGenerate(new BooleanBuilder());
        Case.forField(stringDefault).theOperator(StartsWithOperator.class).andValues("foo")
                .mustGenerate(string.startsWith("foo"));

        Case.forField(stringDefault).theOperator(ContainsOperator.class).andValues("").mustGenerate(new BooleanBuilder());
        Case.forField(stringDefault).theOperator(ContainsOperator.class).andValues("foo").mustGenerate(string.contains("foo"));

        Case.forField(stringDefault).theOperator(EndsWithOperator.class).andValues("").mustGenerate(new BooleanBuilder());
        Case.forField(stringDefault).theOperator(EndsWithOperator.class).andValues("foo").mustGenerate(string.endsWith("foo"));

        Case.forField(stringDefault).theOperator(LikeOperator.class).andValues("").mustGenerate(new BooleanBuilder());
        Case.forField(stringDefault).theOperator(LikeOperator.class).andValues("foo").mustGenerate(string.like("foo"));
    }

    /**
     * @see StringPathFilter
     * @see RegexValidator
     */
    @Test
    public void test_StringPathFilter_RegexValidator() {
        FilterField stringRegex = page.getStringFilterRegex();

        Case.forField(stringRegex).theOperator(null).andValues().mustGenerate(new BooleanBuilder());

        Case.forField(stringRegex).theOperator(IsNullOperator.class).andValues().mustGenerate(string.isNull());

        // validateRegex
        Case.forField(stringRegex).theOperator(EqualsToOperator.class).andValues("").mustFail();
        // validateRegex
        Case.forField(stringRegex).theOperator(EqualsToOperator.class).andValues("foo").mustFail();
        Case.forField(stringRegex).theOperator(EqualsToOperator.class).andValues("1234").mustGenerate(string.eq("1234"));

        // validateRegex
        Case.forField(stringRegex).theOperator(StartsWithOperator.class).andValues("").mustFail();
        // validateRegex
        Case.forField(stringRegex).theOperator(StartsWithOperator.class).andValues("foo").mustFail();
        Case.forField(stringRegex).theOperator(StartsWithOperator.class).andValues("1234")
                .mustGenerate(string.startsWith("1234"));

        // validateRegex
        Case.forField(stringRegex).theOperator(ContainsOperator.class).andValues("").mustFail();
        // validateRegex
        Case.forField(stringRegex).theOperator(ContainsOperator.class).andValues("foo").mustFail();
        Case.forField(stringRegex).theOperator(ContainsOperator.class).andValues("1234").mustGenerate(string.contains("1234"));

        // validateRegex
        Case.forField(stringRegex).theOperator(EndsWithOperator.class).andValues("").mustFail();
        // validateRegex
        Case.forField(stringRegex).theOperator(EndsWithOperator.class).andValues("foo").mustFail();
        Case.forField(stringRegex).theOperator(EndsWithOperator.class).andValues("1234").mustGenerate(string.endsWith("1234"));

        // validateRegex
        Case.forField(stringRegex).theOperator(LikeOperator.class).andValues("").mustFail();
        // validateRegex
        Case.forField(stringRegex).theOperator(LikeOperator.class).andValues("foo").mustFail();
        Case.forField(stringRegex).theOperator(LikeOperator.class).andValues("1234").mustGenerate(string.like("1234"));
    }

}