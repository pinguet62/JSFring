package fr.pinguet62.jsfring.util.cdi.scope;

import javax.enterprise.context.RequestScoped;

import org.springframework.context.annotation.Scope;

/**
 * @see Scope
 * @see RequestScoped
 */
@Scope("request")
public @interface SpringRequestScoped {}