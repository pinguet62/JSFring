package fr.pinguet62.jsfring.webapp.jsf.converter;

import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.service.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Convert {@link Right} to {@link String} value, and conversely, from the primary key.
 */
@Component
@FacesConverter("rightConverter")
public final class RightConverter implements Converter<Right> {

    @Autowired
    private RightService rightService;

    /**
     * @param code The {@link Right#code code}.
     * @return The {@link Right}.
     */
    @Override
    public Right getAsObject(FacesContext context, UIComponent component, String code) {
        return rightService.get(code).block();
    }

    /**
     * @return The {@link Right#code code}.
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Right right) {
        return right.getCode();
    }

}