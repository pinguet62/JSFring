package fr.pinguet62.jsfring.webapp.jsf.config.scope;

import javax.faces.view.ViewScoped;

import org.springframework.context.annotation.Scope;

/**
 * @see Scope
 * @see ViewScoped
 */
@Scope(SpringViewScope.NAME)
public @interface SpringViewScoped {
}