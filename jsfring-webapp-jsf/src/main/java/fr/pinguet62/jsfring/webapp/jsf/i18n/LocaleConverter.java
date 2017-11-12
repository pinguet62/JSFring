package fr.pinguet62.jsfring.webapp.jsf.i18n;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Locale;

/**
 * {@link Converter} for {@link Locale}.
 *
 * @see Locale#toString()
 * @see Locale#Locale(String)
 */
@FacesConverter("localeConverter")
public final class LocaleConverter implements Converter {

    /**
     * @return Locale#Locale(String)
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return new Locale(value);
    }

    /**
     * @return Locale#toString()
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Locale locale = (Locale) value;
        return locale.toString();
    }

}