package fr.pinguet62.jsfring.gui;

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

    /** The selected value. */
    private T selectedValue;

    /**
     * Get the selected value.
     *
     * @return The current {@link #selectedValue value}.
     */
    public T getSelectedValue() {
        return selectedValue;
    }

    /**
     * Set the selected value.
     *
     * @param selectedValue The new {@link #selectedValue value}.
     */
    public void setSelectedValue(T selectedValue) {
        this.selectedValue = selectedValue;
    }

}