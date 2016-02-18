package fr.pinguet62.jsfring.gui.i18n;

import static javax.faces.context.FacesContext.getCurrentInstance;
import static org.apache.commons.collections.IteratorUtils.toList;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

/**
 * Bean used to store the user's {@link Locale}.
 *
 * @see LangFilter
 */
@Named
@Scope("session")
public final class LangBean implements Serializable {

    private static final long serialVersionUID = 1;

    private Locale locale;

    /** @return {@link Locale#getLanguage()} */
    public String getLanguage() {
        return getLocale().getLanguage();
    }

    /** @return {@link #locale} */
    public Locale getLocale() {
        return locale;
    }

    @SuppressWarnings("unchecked")
    public Iterable<Locale> getSupportedLocales() {
        Iterator<Locale> it = getCurrentInstance().getApplication().getSupportedLocales();
        return toList(it);
    }

    /** Initialize the {@link #locale} with default value. */
    @PostConstruct
    private void init() {
        FacesContext context = getCurrentInstance();
        if (context != null)
            locale = context.getViewRoot().getLocale();
    }

    /**
     * Save new {@link Locale}.
     * <p>
     * If {@link FacesContext} is not {@code null}, then the new {@link Locale}
     * will be {@link UIViewRoot#setLocale(Locale) set to ViewRoot}.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
        // ViewRoot
        FacesContext context = getCurrentInstance();
        if (context != null)
            context.getViewRoot().setLocale(locale);
    }

}