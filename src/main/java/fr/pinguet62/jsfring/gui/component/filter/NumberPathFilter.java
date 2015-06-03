package fr.pinguet62.jsfring.gui.component.filter;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;

import org.primefaces.component.selectonemenu.SelectOneMenu;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.NumberExpression;

public final class NumberPathFilter<T extends Number & Comparable<T>>
        implements Supplier<BooleanExpression>, Serializable {

    private static final long serialVersionUID = 1;

    private T leftValue, rigthValue;

    /**
     * The selected {@link Operator} in {@link SelectOneMenu}.
     *
     * @property.getter {@link #getOperator()}
     * @property.setter {@link #setOperator(Operator)}
     */
    private Operator operator = Operator.EQUALS_TO;

    private final NumberExpression<T> path;

    private T singleValue;

    public NumberPathFilter(NumberExpression<T> path) {
        this.path = path;
    }

    // TODO test
    public NumberPathFilter(NumberExpression<T> path, T i) {
        this.path = path;
        singleValue = i;
        leftValue = i;
        rigthValue = i;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof NumberPathFilter))
            return false;
        NumberPathFilter<T> other = (NumberPathFilter<T>) obj;
        return Objects.equals(operator, other.operator)
                && Objects.equals(singleValue, other.singleValue)
                && Objects.equals(leftValue, other.leftValue)
                && Objects.equals(rigthValue, other.rigthValue);
    }

    @Override
    public BooleanExpression get() {
        switch (operator) {
            case BETWEEN:
                return path.between(leftValue, rigthValue);
            case EQUALS_TO:
                return path.eq(singleValue);
            case GREATER_THAN:
                return path.gt(singleValue);
            case IS_NULL:
                return path.isNull();
            case LESS_THAN:
                return path.lt(singleValue);
            default:
                throw new UnsupportedOperationException("Unknown operation: "
                        + operator);
        }
    }

    public T getLeftValue() {
        return leftValue;
    }

    public Operator getOperator() {
        return operator;
    }

    public T getRigthValue() {
        return rigthValue;
    }

    public T getSingleValue() {
        return singleValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, singleValue, leftValue, rigthValue);
    }

    public void setLeftValue(T leftValue) {
        this.leftValue = leftValue;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public void setRigthValue(T rigthValue) {
        this.rigthValue = rigthValue;
    }

    public void setSingleValue(T singleValue) {
        this.singleValue = singleValue;
    }

    @Override
    public String toString() {
        switch (operator.getNumberOfParameters()) {
            case 0:
                return operator.getMessage();
            case 1:
                return String.format("%s(%s)", operator.getMessage(),
                        singleValue);
            case 2:
                return String.format("%s(%s, %s)", operator.getMessage(),
                        leftValue, rigthValue);
            default:
                throw new UnsupportedOperationException(
                        "Invalid number of parameters for operator " + operator);
        }
    }

}