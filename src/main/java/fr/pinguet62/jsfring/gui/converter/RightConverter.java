package fr.pinguet62.jsfring.gui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.model.Right;
import fr.pinguet62.jsfring.service.RightService;

/**
 * Convert {@link Right} to {@link String} value, and conversely, from the
 * primary key.
 */
@Component
@FacesConverter("rightConverter")
public final class RightConverter implements Converter {

    @Autowired
    private RightService rightService;

    /**
     * @param code
     *            The {@link Right#code code}.
     * @return The {@link Right}.
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String code) {
        return rightService.get(code);
    }

    /**
     * @param value
     *            The {@link Right}.
     * @return The {@link Right#code code}.
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object object) {
        Right right = (Right) object;
        return right.getCode();
    }

}
