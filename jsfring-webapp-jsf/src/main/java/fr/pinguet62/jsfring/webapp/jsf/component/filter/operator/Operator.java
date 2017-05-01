package fr.pinguet62.jsfring.webapp.jsf.component.filter.operator;

import java.io.Serializable;
import java.util.List;

import org.primefaces.component.selectonemenu.SelectOneMenu;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.SimpleExpression;

import fr.pinguet62.jsfring.webapp.jsf.component.filter.OperatorConverter;

/**
 * The implementation must:
 * <ul>
 * <li>have a <b>default constructor</b>: the {@link OperatorConverter} use reflection to create new {@link Operator}s;</li>
 * <li><b>{@link Override} {@link Object#equals(Object)}</b> method: JSF component like {@link SelectOneMenu} use this method to
 * check if the value is in the {@link List}).</li>
 * </ul>
 *
 * @param <Exp>
 *            The type of {@link SimpleExpression} on which operator will be applied.
 * @param <T>
 *            The type of parameters.
 */
public interface Operator<Exp extends SimpleExpression<T>, T> extends Serializable {

    /**
     * Apply the {@link Operator} on {@link SimpleExpression path}, with parameters (if required), and generate the
     * {@link BooleanExpression}.
     *
     * @param path
     *            The {@link SimpleExpression} on which apply the operator.
     * @param arg1
     *            The first parameter of operator.<br>
     *            Ignored if the operator doesn't take parameter.
     * @param arg2
     *            The second parameter of operator.<br>
     *            Ignored if the operator take only 1 parameter.
     * @return The {@link BooleanExpression where cause} to apply for filtering.
     */
    Predicate apply(Exp path, T arg1, T arg2);

    default boolean equalsUtil(Object obj) {
        return getClass().equals(obj.getClass());
    }

    /**
     * Get the label of operator.
     *
     * @return The message.
     */
    String getMessage();

    /**
     * Get the number of required parameters.
     *
     * @return The number.
     */
    int getNumberOfParameters();

    default int hashCodeUtil() {
        return getClass().hashCode();
    }

}