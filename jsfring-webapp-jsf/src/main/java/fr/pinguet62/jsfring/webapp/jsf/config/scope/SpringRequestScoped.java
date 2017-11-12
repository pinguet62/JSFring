package fr.pinguet62.jsfring.webapp.jsf.config.scope;

import org.springframework.context.annotation.Scope;

import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

/**
 * Meta-annotation for <i>request</i> {@link Scope}.
 */
@Scope(SCOPE_REQUEST)
public @interface SpringRequestScoped {
}