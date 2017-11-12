package fr.pinguet62.jsfring.webapp.jsf.component.filter;

import fr.pinguet62.jsfring.webapp.jsf.component.filter.operator.Operator;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;

import javax.faces.application.Application;
import javax.faces.component.*;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.event.PostValidateEvent;
import javax.faces.event.PreValidateEvent;
import javax.faces.validator.Validator;
import java.io.IOException;

import static java.util.Arrays.asList;

/**
 * {@link UIComponent} who manage the {@link PathFilter}.
 */
@FacesComponent("fr.pinguet62.jsfring.webapp.jsf.component.filter.PathFilterComponent")
public final class PathFilterComponent extends UIInput implements NamingContainer {

    private enum PropertyKeys {
        operator
    }

    @Getter
    @Setter
    private SelectOneMenu operatorSelectOneMenu;

    @Getter
    @Setter
    private InputText value1InputText, value2InputText;

    /**
     * Bloc of code, skipped in the method {@link UIInput#processValidators(FacesContext)}, because of
     * {@link #setImmediate(boolean)}.
     * <p>
     * This code must be executed after child validations.
     *
     * @see #processValidators(FacesContext)
     */
    private void callSkipped(FacesContext context) {
        if (!isImmediate()) {
            Application application = context.getApplication();
            application.publishEvent(context, PreValidateEvent.class, this);
            executeValidate(context);
            application.publishEvent(context, PostValidateEvent.class, this);
        }
    }

    /**
     * Initialize input fields with initial value passed in parameter.
     */
    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        PathFilter<?, ?> filter = getValue();
        setOperator(filter.getOperator());
        value1InputText.setValue(filter.getValue1());
        value2InputText.setValue(filter.getValue2());

        super.encodeBegin(context);
    }

    /**
     * @see UIInput#executeValidate(FacesContext)
     */
    private void executeValidate(FacesContext context) {
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
    }

    /**
     * Update the {@link PathFilter} with new values.
     *
     * @return The new value.
     */
    @Override
    protected PathFilter<?, ?> getConvertedValue(FacesContext context, Object newSubmittedValue) throws ConverterException {
        PathFilter<?, ?> filter = getValue();
        filter.setOperator((Operator<?, ?>) operatorSelectOneMenu.getValue());
        filter.setValue1(value1InputText.getValue());
        filter.setValue2(value2InputText.getValue());
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

    public Operator<?, ?> getOperator() {
        return (Operator<?, ?>) getStateHelper().get(PropertyKeys.operator);
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
     * @return The value casted to type {@link PathFilter}.
     */
    @Override
    public PathFilter<?, ?> getValue() {
        return (PathFilter<?, ?>) super.getValue();
    }

    /**
     * Set {@link Converter} and {@link Validator} of current component to {@link InputText} children.
     */
    private void initConverterAndValidators() {
        for (InputText inputText : asList(value1InputText, value2InputText)) {
            // Converter
            inputText.setConverter(getConverter());
            // Validator
            for (Validator validator : getValidators())
                if (!asList(inputText.getValidators()).contains(validator))
                    inputText.addValidator(validator);
        }
    }

    /**
     * Validate the components instead of {@link Operator} value. The validation of no-required {@link InputText} are skipped.
     */
    @Override
    public void processValidators(FacesContext context) {
        initConverterAndValidators();

        // "Immediate" to skip block code before foeach on childs
        setImmediate(true);
        super.processValidators(context);
        setImmediate(false);
        callSkipped(context);
    }

    public void setOperator(Operator<?, ?> operator) {
        getStateHelper().put(PropertyKeys.operator, operator);
    }

    /**
     * The value is a complex object, who doesn't require validation.
     *
     * @see UIInput#validateValue(FacesContext, Object)
     */
    @Override
    protected void validateValue(FacesContext context, Object newValue) {
    }

}