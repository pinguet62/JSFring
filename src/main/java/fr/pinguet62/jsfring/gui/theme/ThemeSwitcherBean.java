package fr.pinguet62.jsfring.gui.theme;

import java.io.Serializable;
import java.util.Objects;

import javax.inject.Named;

import fr.pinguet62.jsfring.util.cdi.scope.SpringViewScoped;

/** Used to switch of PrimeFaces {@link Theme}. */
@Named
@SpringViewScoped
public final class ThemeSwitcherBean implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * The current {@link Theme}.<br>
     * Initialized with default value.
     */
    private Theme theme = Theme.fromKey("dark-hive");

    /** Get the current {@link Theme}. */
    public Theme getTheme() {
        return theme;
    }

    /** @return {@link Theme#values()} */
    public Theme[] getThemes() {
        return Theme.values();
    }

    /**
     * Set the new current {@link Theme}.
     *
     * @throws NullPointerException If the parameter is {@code null}.
     */
    public void setTheme(Theme theme) {
        Objects.requireNonNull(theme);
        this.theme = theme;
    }

}