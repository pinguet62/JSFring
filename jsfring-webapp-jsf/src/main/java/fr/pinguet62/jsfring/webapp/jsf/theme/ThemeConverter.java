package fr.pinguet62.jsfring.webapp.jsf.theme;

import static fr.pinguet62.jsfring.webapp.jsf.theme.Theme.fromKey;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * {@link Converter} for {@link Theme}.
 * <p>
 * Uses the {@link Theme#key} as an unique identifier.
 */
@FacesConverter("themeConverter")
public final class ThemeConverter implements Converter {

    /** @see Theme#fromKey(String) */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return fromKey(value);
    }

    /** @return The {@link Theme#toString()} result. */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Theme theme = (Theme) value;
        return theme.toString();
    }

}