package fr.pinguet62.jsfring.webapp.jsf.i18n;

import org.springframework.stereotype.Component;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;

import static java.util.Optional.ofNullable;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static org.apache.commons.collections.IteratorUtils.toList;

/**
 * Bean used to store the user's {@link Locale}.
 *
 * @see LangFilter
 */
@Component
@javax.faces.bean.SessionScoped // TODO @org.springframework.web.context.annotation.SessionScope
public class LangBean implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * {@link Locale} supported by the application.
     */
    private static Collection<Locale> supportedLocales; // Cached

    private Locale locale;

    /**
     * @return {@link Locale#getLanguage()}
     */
    public String getLanguage() {
        return ofNullable(getLocale()).map(Locale::getLanguage).orElse(null);
    }

    /**
     * <i>Lazy loading</i>: initialized when {@code null} and when {@link FacesContext} has
     * {@link FacesContext#getCurrentInstance() current instance}.
     *
     * @return {@link #locale}
     */
    public Locale getLocale() {
        if (locale == null) { // Lazy
            FacesContext context = getCurrentInstance();
            if (context != null)
                locale = context.getViewRoot().getLocale();
        }

        // TODO Solve workaround
        // Force Locale on UIViewRoot
        FacesContext context = getCurrentInstance();
        if (context != null)
            context.getViewRoot().setLocale(locale);

        return locale;
    }

    @SuppressWarnings("unchecked")
    public Collection<Locale> getSupportedLocales() {
        if (supportedLocales == null) // Lazy
            supportedLocales = toList(getCurrentInstance().getApplication().getSupportedLocales());
        return supportedLocales;
    }

    /**
     * Save new {@link Locale}.
     * <p>
     * If {@link FacesContext} is not {@code null}, then the new {@link Locale} will be {@link UIViewRoot#setLocale(Locale) set
     * to ViewRoot}.
     *
     * @param locale {@link #locale}
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
        // ViewRoot
        FacesContext context = getCurrentInstance();
        if (context != null)
            context.getViewRoot().setLocale(locale);
    }

}