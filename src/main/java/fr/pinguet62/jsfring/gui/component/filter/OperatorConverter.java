package fr.pinguet62.jsfring.gui.component.filter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Use the default convertion: {@link Operator#toString()} <>
 * {@link Operator#valueOf(String)}.
 *
 * @see Operator#valueOf(String)
 * @see Operator#toString()
 */
@FacesConverter(value = "fr.pinguet62.jsfring.gui.component.filter.OperatorConverter", forClass = Operator.class)
public final class OperatorConverter implements Converter {

    /** @see Operator#valueOf(String) */
    @Override
    public Operator getAsObject(FacesContext context, UIComponent component,
            String value) {
        return Operator.valueOf(value);
    }

    /** @see @see Operator#toString() */
    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        Operator operator = (Operator) value;
        return operator.toString();
    }

}
