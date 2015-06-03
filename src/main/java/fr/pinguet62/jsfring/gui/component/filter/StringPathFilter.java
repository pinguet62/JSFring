package fr.pinguet62.jsfring.gui.component.filter;

import java.io.Serializable;
import java.util.List;
import java.util.function.Supplier;

import org.primefaces.component.selectonemenu.SelectOneMenu;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.StringPath;

public final class StringPathFilter implements Supplier<BooleanExpression>,
        Serializable {

    private static enum Operator {
        CONTAINS, ENDS_WITH, IS_NULL, LIKE, STARTS_WITH;
    }

    private static final long serialVersionUID = 1;

    private String leftValue, rigthValue;

    private List<String> listValue;

    /**
     * The selected {@link Operator} in {@link SelectOneMenu}.
     *
     * @property.getter {@link #getOperator()}
     * @property.setter {@link #setOperator(Operator)}
     */
    private Operator operator = Operator.CONTAINS;

    private final StringPath path;

    private String singleValue;

    public StringPathFilter(StringPath path) {
        this.path = path;
    }

    @Override
    public BooleanExpression get() {
        switch (operator) {
            case STARTS_WITH:
                return path.startsWith(singleValue);
            case CONTAINS:
                return path.contains(singleValue);
            case ENDS_WITH:
                return path.endsWith(singleValue);
            case LIKE:
                return path.like(singleValue);
            case IS_NULL:
                return path.isNull();
            default:
                throw new UnsupportedOperationException("Unknown operation: "
                        + operator);
        }
    }

    public String getLeftValue() {
        return leftValue;
    }

    public List<String> getListValue() {
        return listValue;
    }

    /** @property.attribute {@link #operator} */
    public Operator getOperator() {
        return operator;
    }

    /**
     * Get available {@link Operator}s.
     *
     * @return {@link Operator#values()}
     */
    public Operator[] getOperators() {
        return Operator.values();
    }

    public String getRigthValue() {
        return rigthValue;
    }

    public String getSingleValue() {
        return singleValue;
    }

    public void setLeftValue(String leftValue) {
        this.leftValue = leftValue;
    }

    public void setListValue(List<String> listValue) {
        this.listValue = listValue;
    }

    /** @property.attribute {@link #operator} */
    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public void setRigthValue(String rigthValue) {
        this.rigthValue = rigthValue;
    }

    public void setSingleValue(String singleValue) {
        this.singleValue = singleValue;
    }

}