package fr.pinguet62.jsfring.gui.component.filter.operator;

import java.io.Serializable;
import java.util.function.BiFunction;

import org.primefaces.component.selectonemenu.SelectOneMenu;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.NumberExpression;
import com.mysema.query.types.expr.SimpleExpression;

import fr.pinguet62.jsfring.gui.component.filter.OperatorConverter;

/**
 * The implementation must:
 * <ul>
 * <li>have a <b>default constructor</b>: the {@link OperatorConverter} use
 * reflection to create new {@link Operator}s;</li>
 * <li><b>{@link Override} {@link #equals(Object)}</b> method: JSF component
 * like {@link SelectOneMenu} use this method to check if the value is in the
 * {@link List}).</li>
 * </ul>
 *
 * @param <Exp> The type of {@link SimpleExpression} on which operator will be
 *            applied.
 * @param <T> The type of parameters.
 */
public interface Operator<Exp extends SimpleExpression<T>, T> extends
        BiFunction<Exp, T[], BooleanExpression>, Serializable {

    /**
     * Apply the operator on {@link NumberExpression path}, with parameters.
     *
     * @return The {@link BooleanExpression where cause} to apply for filtering.
     */
    @Override
    BooleanExpression apply(Exp path, T... args);

    default boolean equalsUtil(Object obj) {
        return getClass().equals(obj.getClass());
    }

    /** Get the label of operator. */
    String getMessage();

    /** Get the number of required parameters. */
    int getNumberOfParameters();

    default int hashCodeUtil() {
        return getClass().hashCode();
    }

}