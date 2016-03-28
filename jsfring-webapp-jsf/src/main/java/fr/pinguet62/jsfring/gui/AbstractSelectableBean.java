package fr.pinguet62.jsfring.gui;

import java.io.Serializable;

/**
 * {@link AbstractBean} with selection management.
 * <p>
 * <b>Multiple selection:</b> not supported.
 */
public abstract class AbstractSelectableBean<T extends Serializable> extends AbstractBean<T> {

    private static final long serialVersionUID = 1;

    /** The selected value. */
    private T selectedValue;

    /**
     * Get the selected value.
     * <p>
     * Used to access to row to managed.
     */
    public T getSelectedValue() {
        return selectedValue;
    }

    /**
     * Set the selected value.
     * <p>
     * Call before each action on row to manage.
     */
    public void setSelectedValue(T selectedValue) {
        this.selectedValue = selectedValue;
    }

}