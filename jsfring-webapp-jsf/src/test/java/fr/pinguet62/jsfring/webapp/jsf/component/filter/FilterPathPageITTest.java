package fr.pinguet62.jsfring.webapp.jsf.component.filter;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.webapp.jsf.component.filter.operator.*;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.filter.FilterField;
import fr.pinguet62.jsfring.webapp.jsf.htmlunit.filter.FilterPathPage;
import fr.pinguet62.jsfring.webapp.jsf.sample.FilterPathBean;
import lombok.Value;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.faces.validator.LongRangeValidator;
import javax.faces.validator.RegexValidator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fr.pinguet62.jsfring.webapp.jsf.htmlunit.AbstractPage.get;
import static fr.pinguet62.jsfring.webapp.jsf.sample.FilterPathBean.EXPRESSION_NUMBER;
import static fr.pinguet62.jsfring.webapp.jsf.sample.FilterPathBean.EXPRESSION_STRING;
import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.collectingAndThen;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

/**
 * @see PathFilter
 * @see FilterPathBean
 * @see StringPathFilter
 * @see RegexValidator
 * @see NumberPathFilter
 * @see LongRangeValidator
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
public class FilterPathPageITTest {

    @Value
    static class TestCase {
        /**
         * Access to the {@link FilterField} to test.
         */
        Function<FilterPathPage, FilterField> fieldFactory;

        /**
         * The selected {@link Operator}.
         */
        Class<? extends Operator> operator;

        /**
         * The input values.
         * <p>
         * An empty array if no parameter.<br>
         * And empty {@link String} if no value.
         */
        String[] values;

        /**
         * The {@link Predicate} result.<br>
         * Throw {@link Exception} if error.
         */
        Predicate result;
    }

    public static Iterable<TestCase> cases() {
        return Stream
                .of(
                        // ===== String

                        new TestCase(FilterPathPage::getStringFilterDefault, null, new String[]{}, new BooleanBuilder()),

                        new TestCase(FilterPathPage::getStringFilterDefault, IsNullOperator.class, new String[]{}, EXPRESSION_STRING.isNull()),

                        new TestCase(FilterPathPage::getStringFilterDefault, EqualsToOperator.class, new String[]{""}, EXPRESSION_STRING.eq("")),
                        new TestCase(FilterPathPage::getStringFilterDefault, EqualsToOperator.class, new String[]{"foo"}, EXPRESSION_STRING.eq("foo")),

                        new TestCase(FilterPathPage::getStringFilterDefault, StartsWithOperator.class, new String[]{""}, new BooleanBuilder()),
                        new TestCase(FilterPathPage::getStringFilterDefault, StartsWithOperator.class, new String[]{"foo"}, EXPRESSION_STRING.startsWith("foo")),

                        new TestCase(FilterPathPage::getStringFilterDefault, ContainsOperator.class, new String[]{""}, new BooleanBuilder()),
                        new TestCase(FilterPathPage::getStringFilterDefault, ContainsOperator.class, new String[]{"foo"}, EXPRESSION_STRING.contains("foo")),

                        new TestCase(FilterPathPage::getStringFilterDefault, EndsWithOperator.class, new String[]{""}, new BooleanBuilder()),
                        new TestCase(FilterPathPage::getStringFilterDefault, EndsWithOperator.class, new String[]{"foo"}, EXPRESSION_STRING.endsWith("foo")),

                        new TestCase(FilterPathPage::getStringFilterDefault, LikeOperator.class, new String[]{""}, new BooleanBuilder()),
                        new TestCase(FilterPathPage::getStringFilterDefault, LikeOperator.class, new String[]{"foo"}, EXPRESSION_STRING.like("foo")),

                        // ===== String: RegexValidator

                        new TestCase(FilterPathPage::getStringFilterRegex, null, new String[]{}, new BooleanBuilder()),
                        new TestCase(FilterPathPage::getStringFilterRegex, IsNullOperator.class, new String[]{}, EXPRESSION_STRING.isNull()),
                        // validateRegex
                        new TestCase(FilterPathPage::getStringFilterRegex, EqualsToOperator.class, new String[]{""}, null),
                        // validateRegex
                        new TestCase(FilterPathPage::getStringFilterRegex, EqualsToOperator.class, new String[]{"foo"}, null),
                        new TestCase(FilterPathPage::getStringFilterRegex, EqualsToOperator.class, new String[]{"1234"}, EXPRESSION_STRING.eq("1234")),
                        // validateRegex
                        new TestCase(FilterPathPage::getStringFilterRegex, StartsWithOperator.class, new String[]{""}, null),
                        // validateRegex
                        new TestCase(FilterPathPage::getStringFilterRegex, StartsWithOperator.class, new String[]{"foo"}, null),
                        new TestCase(FilterPathPage::getStringFilterRegex, StartsWithOperator.class, new String[]{"1234"}, EXPRESSION_STRING.startsWith("1234")),
                        // validateRegex
                        new TestCase(FilterPathPage::getStringFilterRegex, ContainsOperator.class, new String[]{""}, null),
                        // validateRegex
                        new TestCase(FilterPathPage::getStringFilterRegex, ContainsOperator.class, new String[]{"foo"}, null),
                        new TestCase(FilterPathPage::getStringFilterRegex, ContainsOperator.class, new String[]{"1234"}, EXPRESSION_STRING.contains("1234")),
                        // validateRegex
                        new TestCase(FilterPathPage::getStringFilterRegex, EndsWithOperator.class, new String[]{""}, null),
                        // validateRegex
                        new TestCase(FilterPathPage::getStringFilterRegex, EndsWithOperator.class, new String[]{"foo"}, null),
                        new TestCase(FilterPathPage::getStringFilterRegex, EndsWithOperator.class, new String[]{"1234"}, EXPRESSION_STRING.endsWith("1234")),
                        // validateRegex
                        new TestCase(FilterPathPage::getStringFilterRegex, LikeOperator.class, new String[]{""}, null),
                        // validateRegex
                        new TestCase(FilterPathPage::getStringFilterRegex, LikeOperator.class, new String[]{"foo"}, null),
                        new TestCase(FilterPathPage::getStringFilterRegex, LikeOperator.class, new String[]{"1234"}, EXPRESSION_STRING.like("1234")),

                        // ===== Number: Converter

                        new TestCase(FilterPathPage::getNumberFilterDefault, null, new String[]{}, new BooleanBuilder()),
                        new TestCase(FilterPathPage::getNumberFilterDefault, IsNullOperator.class, new String[]{}, EXPRESSION_NUMBER.isNull()),
                        // validateRequired
                        new TestCase(FilterPathPage::getNumberFilterDefault, EqualsToOperator.class, new String[]{""}, null),
                        // converter
                        new TestCase(FilterPathPage::getNumberFilterDefault, EqualsToOperator.class, new String[]{"foo"}, null),
                        new TestCase(FilterPathPage::getNumberFilterDefault, EqualsToOperator.class, new String[]{"1234"}, EXPRESSION_NUMBER.eq(1234)),
                        // validateRequired
                        new TestCase(FilterPathPage::getNumberFilterDefault, GreaterThanOperator.class, new String[]{""}, null),
                        // converter
                        new TestCase(FilterPathPage::getNumberFilterDefault, GreaterThanOperator.class, new String[]{"foo"}, null),
                        new TestCase(FilterPathPage::getNumberFilterDefault, GreaterThanOperator.class, new String[]{"1234"}, EXPRESSION_NUMBER.gt(1234)),
                        // validateRequired
                        new TestCase(FilterPathPage::getNumberFilterDefault, LessThanOperator.class, new String[]{""}, null),
                        // converter
                        new TestCase(FilterPathPage::getNumberFilterDefault, LessThanOperator.class, new String[]{"foo"}, null),
                        new TestCase(FilterPathPage::getNumberFilterDefault, LessThanOperator.class, new String[]{"1234"}, EXPRESSION_NUMBER.lt(1234)),
                        // validateRequired
                        new TestCase(FilterPathPage::getNumberFilterDefault, BetweenOperator.class, new String[]{"", ""}, null),
                        // validateRequired + converter
                        new TestCase(FilterPathPage::getNumberFilterDefault, BetweenOperator.class, new String[]{"", "bar"}, null),
                        // validateRequired
                        new TestCase(FilterPathPage::getNumberFilterDefault, BetweenOperator.class, new String[]{"", "12"}, null),
                        // converter + validateRequired
                        new TestCase(FilterPathPage::getNumberFilterDefault, BetweenOperator.class, new String[]{"foo", ""}, null),
                        // validateRequired
                        new TestCase(FilterPathPage::getNumberFilterDefault, BetweenOperator.class, new String[]{"12", ""}, null),
                        // converter
                        new TestCase(FilterPathPage::getNumberFilterDefault, BetweenOperator.class, new String[]{"foo", "bar"}, null),
                        new TestCase(FilterPathPage::getNumberFilterDefault, BetweenOperator.class, new String[]{"12", "34"}, EXPRESSION_NUMBER.between(12, 34)),

                        // ===== Number: Converter + LongRangeValidator

                        new TestCase(FilterPathPage::getNumberFilterLongRange, null, new String[]{}, new BooleanBuilder()),
                        new TestCase(FilterPathPage::getNumberFilterLongRange, IsNullOperator.class, new String[]{}, EXPRESSION_NUMBER.isNull()),
                        // validateRequired
                        new TestCase(FilterPathPage::getNumberFilterLongRange, EqualsToOperator.class, new String[]{""}, null),
                        // converter
                        new TestCase(FilterPathPage::getNumberFilterLongRange, EqualsToOperator.class, new String[]{"foo"}, null),
                        // validateLongRange
                        new TestCase(FilterPathPage::getNumberFilterLongRange, EqualsToOperator.class, new String[]{"0"}, null),
                        // validateLongRange
                        new TestCase(FilterPathPage::getNumberFilterLongRange, EqualsToOperator.class, new String[]{"999"}, null),
                        new TestCase(FilterPathPage::getNumberFilterLongRange, EqualsToOperator.class, new String[]{"12"}, EXPRESSION_NUMBER.eq(12)),
                        // validateRequired
                        new TestCase(FilterPathPage::getNumberFilterLongRange, GreaterThanOperator.class, new String[]{""}, null),
                        // converter
                        new TestCase(FilterPathPage::getNumberFilterLongRange, GreaterThanOperator.class, new String[]{"foo"}, null),
                        // validateLongRange
                        new TestCase(FilterPathPage::getNumberFilterLongRange, GreaterThanOperator.class, new String[]{"0"}, null),
                        // validateLongRange
                        new TestCase(FilterPathPage::getNumberFilterLongRange, GreaterThanOperator.class, new String[]{"999"}, null),
                        new TestCase(FilterPathPage::getNumberFilterLongRange, GreaterThanOperator.class, new String[]{"12"}, EXPRESSION_NUMBER.gt(12)),
                        // validateRequired
                        new TestCase(FilterPathPage::getNumberFilterLongRange, LessThanOperator.class, new String[]{""}, null),
                        // converter
                        new TestCase(FilterPathPage::getNumberFilterLongRange, LessThanOperator.class, new String[]{"foo"}, null),
                        // validateLongRange
                        new TestCase(FilterPathPage::getNumberFilterLongRange, LessThanOperator.class, new String[]{"0"}, null),
                        // validateLongRange
                        new TestCase(FilterPathPage::getNumberFilterLongRange, LessThanOperator.class, new String[]{"999"}, null),
                        new TestCase(FilterPathPage::getNumberFilterLongRange, LessThanOperator.class, new String[]{"12"}, EXPRESSION_NUMBER.lt(12)),
                        // validateRequired
                        new TestCase(FilterPathPage::getNumberFilterLongRange, BetweenOperator.class, new String[]{"", ""}, null),
                        // validateRequired + converter
                        new TestCase(FilterPathPage::getNumberFilterLongRange, BetweenOperator.class, new String[]{"", "bar"}, null),
                        // validateRequired
                        new TestCase(FilterPathPage::getNumberFilterLongRange, BetweenOperator.class, new String[]{"", "12"}, null),
                        // converter + validateRequired
                        new TestCase(FilterPathPage::getNumberFilterLongRange, BetweenOperator.class, new String[]{"foo", ""}, null),
                        // validateRequired
                        new TestCase(FilterPathPage::getNumberFilterLongRange, BetweenOperator.class, new String[]{"12", ""}, null),
                        new TestCase(FilterPathPage::getNumberFilterLongRange, BetweenOperator.class, new String[]{"foo", "bar"}, null),
                        // validateRequired
                        new TestCase(FilterPathPage::getNumberFilterLongRange, BetweenOperator.class, new String[]{"0", "999"}, null),
                        // validateRequired
                        new TestCase(FilterPathPage::getNumberFilterLongRange, BetweenOperator.class, new String[]{"12", "999"}, null),
                        // validateRequired
                        new TestCase(FilterPathPage::getNumberFilterLongRange, BetweenOperator.class, new String[]{"0", "34"}, null),
                        new TestCase(FilterPathPage::getNumberFilterLongRange, BetweenOperator.class, new String[]{"12", "34"}, EXPRESSION_NUMBER.between(12, 34))
                )
                .limit(10) // performance
                .collect(collectingAndThen(Collectors.toList(),
                        list -> {
                            shuffle(list);
                            return list;
                        }));
    }

    @ParameterizedTest
    @MethodSource("cases")
    public void test(TestCase testCase) {
        FilterPathPage page = get().gotoSampleFilterSimple();
        FilterField field = testCase.getFieldFactory().apply(page);
        // fill
        field.setOperator(testCase.getOperator());
        for (int i = 0; i < testCase.getValues().length; i++)
            field.setValue(i, testCase.getValues()[i]);
        // process
        field.submit();
        // check
        if (testCase.getResult() == null) {
            assertThat(field.isError(), is(true));
        } else {
            assertThat(field.isError(), is(false));
            assertThat(field.getQuery(), is(equalTo(testCase.getResult().toString())));
        }
    }

}