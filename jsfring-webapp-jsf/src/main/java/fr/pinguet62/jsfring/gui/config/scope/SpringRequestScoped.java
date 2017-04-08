package fr.pinguet62.jsfring.gui.config.scope;

import javax.enterprise.context.RequestScoped;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

/**
 * @see Scope
 * @see RequestScoped
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
public @interface SpringRequestScoped {
}