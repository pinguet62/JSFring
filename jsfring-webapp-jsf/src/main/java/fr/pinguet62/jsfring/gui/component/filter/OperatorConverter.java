package fr.pinguet62.jsfring.gui.component.filter;

import static java.lang.Class.forName;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import fr.pinguet62.jsfring.gui.component.filter.operator.Operator;

/** Use the {@link Class#getName() class name} as key for {@link Operator}. */
@FacesConverter(value = "fr.pinguet62.jsfring.gui.component.filter.OperatorConverter", forClass = Operator.class)
public final class OperatorConverter implements Converter {

    /** @see Class#newInstance() */
    @Override
    public Operator<?, ?> getAsObject(FacesContext context, UIComponent component, String className) {
        try {
            return (Operator<?, ?>) forName(className).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Error during creation of operator", e);
        }
    }

    /**
     * @return The converted {@link Operator}.<br>
     *         If {@code value == null}, then return {@code null}.
     * @see Class#getName()
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null)
            return null;
        Operator<?, ?> operator = (Operator<?, ?>) value;
        return operator.getClass().getName();
    }

}
