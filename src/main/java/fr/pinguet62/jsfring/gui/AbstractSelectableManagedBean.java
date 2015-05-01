package fr.pinguet62.jsfring.gui;

/** {@link AbstractManagedBean} with gestion of selected value. */
public abstract class AbstractSelectableManagedBean<T> extends
        AbstractManagedBean<T> {

    private static final long serialVersionUID = 1;

    /**
     * The selected value.
     *
     * @property.getter {@link #getSelectedValue()}
     * @property.setter {@link #setSelectedValue(Object)}
     */
    private T selectedValue;

    /**
     * Get the selected value.
     *
     * @property.attribute {@link #selectedValue}
     */
    public T getSelectedValue() {
        return selectedValue;
    }

    /**
     * Set the selected value.
     *
     * @property.attribute {@link #selectedValue}
     */
    public void setSelectedValue(T selectedValue) {
        this.selectedValue = selectedValue;
    }

}
