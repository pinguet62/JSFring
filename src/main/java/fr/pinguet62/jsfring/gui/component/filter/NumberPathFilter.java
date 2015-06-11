package fr.pinguet62.jsfring.gui.component.filter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.primefaces.component.selectonemenu.SelectOneMenu;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.NumberExpression;

import fr.pinguet62.jsfring.gui.component.filter.operator.BetweenOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.EqualsToOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.IsNullOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.Operator;

public final class NumberPathFilter<T extends Number & Comparable<T>>
        implements Supplier<BooleanExpression>, Serializable {

    private static final long serialVersionUID = 1;

    /**
     * The selected {@link Operator} in {@link SelectOneMenu}.
     *
     * @property.getter {@link #getOperator()}
     * @property.setter {@link #setOperator(Operator)}
     */
    private Operator<T> operator = new EqualsToOperator<>();

    private final NumberExpression<T> path;

    private T value1, value2;

    public NumberPathFilter(NumberExpression<T> path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof NumberPathFilter))
            return false;
        NumberPathFilter<T> other = (NumberPathFilter<T>) obj;
        return Objects.equals(operator, other.operator)
                && Objects.equals(value1, other.value1)
                && Objects.equals(value2, other.value2);
    }

    @Override
    public BooleanExpression get() {
        return operator.apply(path, value1, value2);
    }

    public Operator getOperator() {
        return operator;
    }

    /**
     * Get the list of {@link Operator}s, applicable on {@link #path}.
     * <p>
     * Override this method to add/remove/change the available filters.
     *
     * @return The ordered list of {@link Operator}s.
     */
    public List<Operator<T>> getOperators() {
        return Arrays.asList(new IsNullOperator<T>(),
                new EqualsToOperator<T>(), new BetweenOperator<T>());
    }

    public T getValue1() {
        return value1;
    }

    public T getValue2() {
        return value2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, value2, value1);
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public void setValue1(T value1) {
        this.value1 = value1;
    }

    public void setValue2(T value2) {
        this.value2 = value2;
    }

    @Override
    public String toString() {
        switch (operator.getNumberOfParameters()) {
            case 0:
                return operator.getMessage();
            case 1:
                return String.format("%s(%s)", operator.getMessage(), value1);
            case 2:
                return String.format("%s(%s, %s)", operator.getMessage(),
                        value1, value2);
            default:
                throw new UnsupportedOperationException(
                        "Invalid number of parameters for operator " + operator);
        }
    }

}