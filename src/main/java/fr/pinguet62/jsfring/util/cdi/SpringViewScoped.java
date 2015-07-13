package fr.pinguet62.jsfring.util.cdi;

import javax.faces.view.ViewScoped;

import org.springframework.context.annotation.Scope;

/**
 * @see Scope
 * @see ViewScoped
 */
@Scope("view")
public @interface SpringViewScoped {}
