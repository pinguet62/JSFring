package fr.pinguet62.jsfring.gui.theme;

import static fr.pinguet62.jsfring.gui.theme.Theme.fromKey;
import static fr.pinguet62.jsfring.gui.theme.Theme.values;
import static java.util.Objects.requireNonNull;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/** Used to switch of PrimeFaces {@link Theme}. */
@Named
@SessionScoped
public final class ThemeSwitcherBean implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * The current {@link Theme}.<br>
     * Initialized with default value.
     */
    private Theme theme = fromKey("dark-hive");

    /** Get the current {@link Theme}. */
    public Theme getTheme() {
        return theme;
    }

    /** @return {@link Theme#values()} */
    public Theme[] getThemes() {
        return values();
    }

    /**
     * Set the new current {@link Theme}.
     *
     * @throws NullPointerException If the parameter is {@code null}.
     */
    public void setTheme(Theme theme) {
        requireNonNull(theme);
        this.theme = theme;
    }

}