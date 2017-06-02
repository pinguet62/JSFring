package fr.pinguet62.jsfring.webapp.jsf.theme;

import static fr.pinguet62.jsfring.webapp.jsf.theme.Theme.fromKey;
import static fr.pinguet62.jsfring.webapp.jsf.theme.Theme.values;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

/** Used to switch of PrimeFaces {@link Theme}. */
@Named
@SessionScoped
public final class ThemeSwitcherBean implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * The current {@link Theme}.<br>
     * Initialized with default value.
     */
    @Getter
    @Setter
    private Theme theme = fromKey("dark-hive");

    /** @return {@link Theme#values()} */
    public Theme[] getThemes() {
        return values();
    }

}