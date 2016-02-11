package fr.pinguet62.jsfring.gui.component.filter;

import static fr.pinguet62.jsfring.gui.sample.FilterPathBean.EXPRESSION_NUMBER;
import static fr.pinguet62.jsfring.gui.sample.FilterPathBean.EXPRESSION_STRING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.function.Function;

import javax.faces.validator.LongRangeValidator;
import javax.faces.validator.RegexValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestContextManager;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.gui.component.filter.operator.BetweenOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.ContainsOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.EndsWithOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.EqualsToOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.GreaterThanOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.IsNullOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.LessThanOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.LikeOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.Operator;
import fr.pinguet62.jsfring.gui.component.filter.operator.StartsWithOperator;
import fr.pinguet62.jsfring.gui.htmlunit.AbstractPage;
import fr.pinguet62.jsfring.gui.htmlunit.filter.FilterField;
import fr.pinguet62.jsfring.gui.htmlunit.filter.FilterPathPage;
import fr.pinguet62.jsfring.gui.sample.FilterPathBean;

/**
 * @see PathFilter
 * @see FilterPathBean
 * @see StringPathFilter
 * @see RegexValidator
 * @see NumberPathFilter
 * @see LongRangeValidator
 */
@RunWith(Parameterized.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@WebIntegrationTest
public class FilterPathPageITTest {

    // Because lambda cannot be used into inline array
    private static final Function<FilterPathPage, FilterField> fieldNumber = FilterPathPage::getNumberFilterDefault;
    private static final Function<FilterPathPage, FilterField> fieldNumberLongRange = FilterPathPage::getNumberFilterLongRange;
    private static final Function<FilterPathPage, FilterField> fieldString = FilterPathPage::getStringFilterDefault;
    private static final Function<FilterPathPage, FilterField> fieldStringRegex = FilterPathPage::getStringFilterRegex;

    /**
     * The test cases.<br>
     * Values correspond to arguments of
     * {@link #FilterPathPageTest(Function, Class, String[], Predicate)
     * constructor}.
     */
    @Parameters
    public static Iterable<Object[]> params() {
        return Arrays.asList(new Object[][] { //
                // ===== String

                { fieldString, null, new String[] {}, new BooleanBuilder() },

                { fieldString, IsNullOperator.class, new String[] {}, EXPRESSION_STRING.isNull() },

                { fieldString, EqualsToOperator.class, new String[] { "" }, EXPRESSION_STRING.eq("") },
                { fieldString, EqualsToOperator.class, new String[] { "foo" }, EXPRESSION_STRING.eq("foo") },

                { fieldString, StartsWithOperator.class, new String[] { "" }, new BooleanBuilder() },
                { fieldString, StartsWithOperator.class, new String[] { "foo" }, EXPRESSION_STRING.startsWith("foo") },

                { fieldString, ContainsOperator.class, new String[] { "" }, new BooleanBuilder() },
                { fieldString, ContainsOperator.class, new String[] { "foo" }, EXPRESSION_STRING.contains("foo") },

                { fieldString, EndsWithOperator.class, new String[] { "" }, new BooleanBuilder() },
                { fieldString, EndsWithOperator.class, new String[] { "foo" }, EXPRESSION_STRING.endsWith("foo") },

                { fieldString, LikeOperator.class, new String[] { "" }, new BooleanBuilder() },
                { fieldString, LikeOperator.class, new String[] { "foo" }, EXPRESSION_STRING.like("foo") },

                // ===== String: RegexValidator

                { fieldStringRegex, null, new String[] {}, new BooleanBuilder() },

                { fieldStringRegex, IsNullOperator.class, new String[] {}, EXPRESSION_STRING.isNull() },

                // validateRegex
                { fieldStringRegex, EqualsToOperator.class, new String[] { "" }, null },
                // validateRegex
                { fieldStringRegex, EqualsToOperator.class, new String[] { "foo" }, null },
                { fieldStringRegex, EqualsToOperator.class, new String[] { "1234" }, EXPRESSION_STRING.eq("1234") },

                // validateRegex
                { fieldStringRegex, StartsWithOperator.class, new String[] { "" }, null },
                // validateRegex
                { fieldStringRegex, StartsWithOperator.class, new String[] { "foo" }, null },
                { fieldStringRegex, StartsWithOperator.class, new String[] { "1234" }, EXPRESSION_STRING.startsWith("1234") },

                // validateRegex
                { fieldStringRegex, ContainsOperator.class, new String[] { "" }, null },
                // validateRegex
                { fieldStringRegex, ContainsOperator.class, new String[] { "foo" }, null },
                { fieldStringRegex, ContainsOperator.class, new String[] { "1234" }, EXPRESSION_STRING.contains("1234") },

                // validateRegex
                { fieldStringRegex, EndsWithOperator.class, new String[] { "" }, null },
                // validateRegex
                { fieldStringRegex, EndsWithOperator.class, new String[] { "foo" }, null },
                { fieldStringRegex, EndsWithOperator.class, new String[] { "1234" }, EXPRESSION_STRING.endsWith("1234") },

                // validateRegex
                { fieldStringRegex, LikeOperator.class, new String[] { "" }, null },
                // validateRegex
                { fieldStringRegex, LikeOperator.class, new String[] { "foo" }, null },
                { fieldStringRegex, LikeOperator.class, new String[] { "1234" }, EXPRESSION_STRING.like("1234") },

                // ===== Number: Converter

                { fieldNumber, null, new String[] {}, new BooleanBuilder() },

                { fieldNumber, IsNullOperator.class, new String[] {}, EXPRESSION_NUMBER.isNull() },

                // validateRequired
                { fieldNumber, EqualsToOperator.class, new String[] { "" }, null },
                // converter
                { fieldNumber, EqualsToOperator.class, new String[] { "foo" }, null },
                { fieldNumber, EqualsToOperator.class, new String[] { "1234" }, EXPRESSION_NUMBER.eq(1234) },

                // validateRequired
                { fieldNumber, GreaterThanOperator.class, new String[] { "" }, null },
                // converter
                { fieldNumber, GreaterThanOperator.class, new String[] { "foo" }, null },
                { fieldNumber, GreaterThanOperator.class, new String[] { "1234" }, EXPRESSION_NUMBER.gt(1234) },

                // validateRequired
                { fieldNumber, LessThanOperator.class, new String[] { "" }, null },
                // converter
                { fieldNumber, LessThanOperator.class, new String[] { "foo" }, null },
                { fieldNumber, LessThanOperator.class, new String[] { "1234" }, EXPRESSION_NUMBER.lt(1234) },

                // validateRequired
                { fieldNumber, BetweenOperator.class, new String[] { "", "" }, null },
                // validateRequired + converter
                { fieldNumber, BetweenOperator.class, new String[] { "", "bar" }, null },
                // validateRequired
                { fieldNumber, BetweenOperator.class, new String[] { "", "12" }, null },
                // converter + validateRequired
                { fieldNumber, BetweenOperator.class, new String[] { "foo", "" }, null },
                // validateRequired
                { fieldNumber, BetweenOperator.class, new String[] { "12", "" }, null },
                // converter
                { fieldNumber, BetweenOperator.class, new String[] { "foo", "bar" }, null },
                { fieldNumber, BetweenOperator.class, new String[] { "12", "34" }, EXPRESSION_NUMBER.between(12, 34) },

                // ===== Number: Converter + LongRangeValidator

                { fieldNumberLongRange, null, new String[] {}, new BooleanBuilder() },

                { fieldNumberLongRange, IsNullOperator.class, new String[] {}, EXPRESSION_NUMBER.isNull() },

                // validateRequired
                { fieldNumberLongRange, EqualsToOperator.class, new String[] { "" }, null },
                // converter
                { fieldNumberLongRange, EqualsToOperator.class, new String[] { "foo" }, null },
                // validateLongRange
                { fieldNumberLongRange, EqualsToOperator.class, new String[] { "0" }, null },
                // validateLongRange
                { fieldNumberLongRange, EqualsToOperator.class, new String[] { "999" }, null },
                { fieldNumberLongRange, EqualsToOperator.class, new String[] { "12" }, EXPRESSION_NUMBER.eq(12) },

                // validateRequired
                { fieldNumberLongRange, GreaterThanOperator.class, new String[] { "" }, null },
                // converter
                { fieldNumberLongRange, GreaterThanOperator.class, new String[] { "foo" }, null },
                // validateLongRange
                { fieldNumberLongRange, GreaterThanOperator.class, new String[] { "0" }, null },
                // validateLongRange
                { fieldNumberLongRange, GreaterThanOperator.class, new String[] { "999" }, null },
                { fieldNumberLongRange, GreaterThanOperator.class, new String[] { "12" }, EXPRESSION_NUMBER.gt(12) },

                // validateRequired
                { fieldNumberLongRange, LessThanOperator.class, new String[] { "" }, null },
                // converter
                { fieldNumberLongRange, LessThanOperator.class, new String[] { "foo" }, null },
                // validateLongRange
                { fieldNumberLongRange, LessThanOperator.class, new String[] { "0" }, null },
                // validateLongRange
                { fieldNumberLongRange, LessThanOperator.class, new String[] { "999" }, null },
                { fieldNumberLongRange, LessThanOperator.class, new String[] { "12" }, EXPRESSION_NUMBER.lt(12) },

                // validateRequired
                { fieldNumberLongRange, BetweenOperator.class, new String[] { "", "" }, null },
                // validateRequired + converter
                { fieldNumberLongRange, BetweenOperator.class, new String[] { "", "bar" }, null },
                // validateRequired
                { fieldNumberLongRange, BetweenOperator.class, new String[] { "", "12" }, null },
                // converter + validateRequired
                { fieldNumberLongRange, BetweenOperator.class, new String[] { "foo", "" }, null },
                // validateRequired
                { fieldNumberLongRange, BetweenOperator.class, new String[] { "12", "" }, null },
                { fieldNumberLongRange, BetweenOperator.class, new String[] { "foo", "bar" }, null },
                // validateRequired
                { fieldNumberLongRange, BetweenOperator.class, new String[] { "0", "999" }, null },
                // validateRequired
                { fieldNumberLongRange, BetweenOperator.class, new String[] { "12", "999" }, null },
                // validateRequired
                { fieldNumberLongRange, BetweenOperator.class, new String[] { "0", "34" }, null },
                { fieldNumberLongRange, BetweenOperator.class, new String[] { "12", "34" }, EXPRESSION_NUMBER.between(12, 34) }

                // =====
        });
    }

    // Test parameters
    private final Function<FilterPathPage, FilterField> fieldFactory;

    private final Class<?> operator;
    private final Predicate result;
    private final String[] values;

    /**
     * @param fieldFactory The function to get {@link FilterField} to test from
     *            {@link FilterPathPage}.
     * @param operator The {@link Operator} implementation to choose.
     * @param values The values.<br>
     *            All values that the operator has parameters<br>
     *            An empty array if no parameter.
     * @param result The {@link Predicate} result.<br>
     *            {@code null} for fail.
     */
    public FilterPathPageITTest(Function<FilterPathPage, FilterField> fieldFactory, Class<?> operator, String[] values,
            Predicate result) throws Exception {
        new TestContextManager(getClass()).prepareTestInstance(this); // @RunWith(SpringJUnit4ClassRunner.class)
        this.fieldFactory = fieldFactory;
        this.operator = operator;
        this.values = values;
        this.result = result;
    }

    @Test
    public void test() {
        FilterPathPage page = AbstractPage.get().gotoSampleFilterSimple();
        FilterField field = fieldFactory.apply(page);
        field.setOperator(operator);
        for (int i = 0; i < values.length; i++)
            field.setValue(i, values[i]);
        field.submit();
        if (result == null)
            assertTrue(field.isError());
        else {
            assertFalse(field.isError());
            assertEquals(result.toString(), field.getQuery());
        }
    }

}