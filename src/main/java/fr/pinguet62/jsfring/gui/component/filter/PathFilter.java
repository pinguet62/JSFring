package fr.pinguet62.jsfring.gui.component.filter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.SimpleExpression;

import fr.pinguet62.jsfring.gui.component.filter.operator.EqualsToOperator;
import fr.pinguet62.jsfring.gui.component.filter.operator.Operator;

public abstract class PathFilter<Exp extends SimpleExpression<T>, T> implements
Supplier<BooleanExpression>, Serializable {

    private static final long serialVersionUID = 1;

    /**
     * The {@link Operator} to apply on {@link #path}.
     *
     * @property.getter {@link #getOperator()}
     * @property.setter {@link #setOperator(Operator)}
     */
    private Operator<Exp, T> operator = new EqualsToOperator<Exp, T>();

    private final Exp path;

    private T value1, value2;

    public PathFilter(Exp path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof PathFilter))
            return false;
        PathFilter<Exp, T> other = (PathFilter<Exp, T>) obj;
        return Objects.equals(operator, other.operator)
                && Objects.equals(value1, other.value1)
                && Objects.equals(value2, other.value2);
    }

    @Override
    public BooleanExpression get() {
        return operator.apply(path, value1, value2);
    }

    public Operator<Exp, T> getOperator() {
        return operator;
    }

    /**
     * Get the list of {@link Operator}s, applicable on {@link #path}.
     * <p>
     * Override this method to adapt available filters as required.
     *
     * @return The ordered list of {@link Operator}s.
     */
    public abstract List<Operator<Exp, T>> getOperators();

    public T getValue1() {
        return value1;
    }

    public T getValue2() {
        return value2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, value1, value2);
    }

    /**
     * TODO Change visibility and argument type of this methos See
     * {@link PathFilterComponent#getConvertedValue(javax.faces.context.FacesContext, Object)}
     */
    void setOperator(Operator<?, ?> operator2) {
        this.operator = (Operator<Exp, T>) operator2;
    }

    /**
     * TODO Change visibility and argument type of this methos See
     * {@link PathFilterComponent#getConvertedValue(javax.faces.context.FacesContext, Object)}
     */
    void setValue1(Object object) {
        this.value1 = (T) object;
    }

    /**
     * TODO Change visibility and argument type of this methos See
     * {@link PathFilterComponent#getConvertedValue(javax.faces.context.FacesContext, Object)}
     */
    void setValue2(Object value2) {
        this.value2 = (T) value2;
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