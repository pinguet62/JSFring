package fr.pinguet62.jsfring.gui.i18n;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

/**
 * Bean used to change dynamically the user's {@link Locale}.
 * <p>
 * The new {@link Locale} must by passed by GET, into URL.<br>
 * The key is {@code lang}.<br>
 * The value can be any, but if it is not supported the language will not
 * change.
 *
 * @see LangFilter
 */
@Named
@Scope("session")
public final class LangBean implements Serializable {

    public static final String PARAMETER = "lang";

    private static final long serialVersionUID = 1;

    private Locale locale;

    public String getLanguage() {
        return getLocale().getLanguage();
    }

    /**
     * Check GET parameters: if the {@link #PARAMETER} is set, so the user's
     * {@link Locale} will be updated.
     */
    public Locale getLocale() {
        FacesContext context = FacesContext.getCurrentInstance();

        // Check GET parameter
        Map<String, String> parameters = context.getExternalContext().getRequestParameterMap();
        String strLocale = parameters.get(PARAMETER);
        if (strLocale != null)
            setLocale(new Locale(strLocale));

        return locale;
    }

    /** Initialize the {@link #locale} with default value. */
    @PostConstruct
    private void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null)
            locale = context.getViewRoot().getLocale();
    }

    /**
     * Save new {@link Locale} into user's session.<br>
     * If {@link FacesContext} is not {@code null}: set new {@link Locale} into
     * {@link UIViewRoot#setLocale(Locale)}.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
        // ViewRoot
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null)
            context.getViewRoot().setLocale(locale);
    }

}