package fr.pinguet62.jsfring.webapp.jsf.theme;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static fr.pinguet62.jsfring.webapp.jsf.theme.Theme.fromKey;
import static fr.pinguet62.jsfring.webapp.jsf.theme.Theme.values;

/**
 * Used to switch of PrimeFaces {@link Theme}.
 */
@Component
@javax.faces.bean.SessionScoped // TODO @org.springframework.web.context.annotation.SessionScope
public class ThemeSwitcherBean implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * The current {@link Theme}.<br>
     * Initialized with default value.
     */
    @Getter
    @Setter
    private Theme theme = fromKey("dark-hive");

    /**
     * @return {@link Theme#values()}
     */
    public Theme[] getThemes() {
        return values();
    }

}