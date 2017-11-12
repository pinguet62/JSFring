package fr.pinguet62.jsfring.webapp.jsf.config.scope;

import org.springframework.context.annotation.Scope;

import javax.faces.view.ViewScoped;

/**
 * @see Scope
 * @see ViewScoped
 */
@Scope(SpringViewScope.NAME)
public @interface SpringViewScoped {
}