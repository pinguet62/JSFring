package fr.pinguet62.jsfring.gui.component.filter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import fr.pinguet62.jsfring.gui.component.filter.operator.Operator;

/** Use the default class name as key for {@link Operator}. */
@FacesConverter(value = "fr.pinguet62.jsfring.gui.component.filter.OperatorConverter", forClass = Operator.class)
public final class OperatorConverter implements Converter {

    /** @see Class#newInstance() */
    @Override
    public Operator<?> getAsObject(FacesContext context, UIComponent component,
            String className) {
        try {
            return (Operator<?>) Class.forName(className).newInstance();
        } catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException e) {
            throw new IllegalArgumentException(
                    "Error during creation of operator", e);
        }
    }

    /** @see Class#getName() */
    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        Operator<?> operator = (Operator<?>) value;
        return operator.getClass().getName();
    }

}
