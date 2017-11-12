package fr.pinguet62.jsfring.webapp.jsf;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * {@link AbstractBean} with single selection management.<br>
 * <i>Multiple selection</i> is not supported.
 * <p>
 * <code>&lt;f:setPropertyActionListener target="#{myBean.selectedValue}" value="..." /&gt;</code>
 *
 * @param <T> The type of objects to display.
 */
public abstract class AbstractSelectableBean<T extends Serializable> extends AbstractBean<T> {

    private static final long serialVersionUID = 1;

    /**
     * The selected value.
     */
    @Getter
    @Setter
    private T selectedValue;

}