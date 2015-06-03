package fr.pinguet62.jsfring.gui.component.filter;

import java.io.IOException;

import javax.faces.application.Application;
import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.event.PostValidateEvent;
import javax.faces.event.PreValidateEvent;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;

/** Classe who manage the filter (operator & value) {@link UIComponent}. */
@FacesComponent(value = "filter")
public final class FilterComponent extends UIInput implements NamingContainer {

    private InputText leftValueInputText, rigthValueInputText;

    private SelectOneMenu operatorSelectOneMenu;

    private InputText singleValueInputText;

    /**
     * The methods of {@link UIInput#processValidators(FacesContext)} who was
     * skiped by the {@link #setImmediate(boolean)} method.
     */
    private void callSkipped(FacesContext context) {
        if (!isImmediate()) {
            Application application = context.getApplication();
            application.publishEvent(context, PreValidateEvent.class, this);

            // super.executeValidate(FacesContext context)
            try {
                validate(context);
            } catch (RuntimeException e) {
                context.renderResponse();
                throw e;
            }
            if (!isValid()) {
                context.validationFailed();
                context.renderResponse();
            }

            application.publishEvent(context, PostValidateEvent.class, this);
        }
    }

    /** Initialize input fields with initial value passed in parameter. */
    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        NumberPathFilter<Integer> filter = getValue();

        operatorSelectOneMenu.setValue(filter.getOperator());
        singleValueInputText.setValue(filter.getSingleValue());
        leftValueInputText.setValue(filter.getLeftValue());
        rigthValueInputText.setValue(filter.getRigthValue());

        super.encodeBegin(context);
    }

    /**
     * Update the initial value with new values.
     *
     * @return The new value.
     */
    @Override
    protected NumberPathFilter<Integer> getConvertedValue(FacesContext context,
            Object newSubmittedValue) throws ConverterException {
        NumberPathFilter<Integer> filter = getValue();

        // Update new values
        filter.setOperator((Operator) operatorSelectOneMenu.getValue());
        filter.setSingleValue((Integer) singleValueInputText.getValue());
        filter.setLeftValue((Integer) leftValueInputText.getValue());
        filter.setRigthValue((Integer) rigthValueInputText.getValue());

        return filter;
    }

    /**
     * Returns the component family of {@link UINamingContainer}.<br>
     * Required by composite component.
     */
    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    public InputText getLeftValueInputText() {
        return leftValueInputText;
    }

    /**
     * List of available {@link Operator}s.
     *
     * @see Operator#values()
     */
    public Operator[] getOperators() {
        return Operator.values();
    }

    public SelectOneMenu getOperatorSelectOneMenu() {
        return operatorSelectOneMenu;
    }

    public InputText getRigthValueInputText() {
        return rigthValueInputText;
    }

    public InputText getSingleValueInputText() {
        return singleValueInputText;
    }

    /**
     * {@inheritDoc}
     *
     * @return This (not null) object.
     */
    @Override
    public Object getSubmittedValue() {
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return The value casted to type {@link NumberPathFilter}.
     */
    @Override
    public NumberPathFilter<Integer> getValue() {
        return (NumberPathFilter<Integer>) super.getValue();
    }

    /**
     * Validate the components instead of {@link Operator} value. The validation
     * of no-requiered {@link InputText} are skipped.
     */
    // TODO change using of "immediate" attribute to another good method
    @Override
    public void processValidators(FacesContext context) {
        Operator operator = new OperatorConverter().getAsObject(
                FacesContext.getCurrentInstance(), operatorSelectOneMenu,
                (String) operatorSelectOneMenu.getSubmittedValue());
        switch (operator.getNumberOfParameters()) {
            case 0:
                singleValueInputText.setImmediate(true);
                leftValueInputText.setImmediate(true);
                rigthValueInputText.setImmediate(true);
                break;
            case 1:
                leftValueInputText.setImmediate(true);
                rigthValueInputText.setImmediate(true);
                break;
            case 2:
                singleValueInputText.setImmediate(true);
                break;
            default:
                throw new IllegalArgumentException("Unknow operator: "
                        + operator);
        }
        setImmediate(true);

        super.processValidators(context);

        setImmediate(false);
        callSkipped(context);
        singleValueInputText.setImmediate(false);
        leftValueInputText.setImmediate(false);
        rigthValueInputText.setImmediate(false);
    }

    public void setLeftValueInputText(InputText leftValueInputText) {
        this.leftValueInputText = leftValueInputText;
    }

    public void setOperatorSelectOneMenu(SelectOneMenu operatorSelectOneMenu) {
        this.operatorSelectOneMenu = operatorSelectOneMenu;
    }

    public void setRigthValueInputText(InputText rigthValueInputText) {
        this.rigthValueInputText = rigthValueInputText;
    }

    public void setSingleValueInputText(InputText singleValueInputText) {
        this.singleValueInputText = singleValueInputText;
    }

}