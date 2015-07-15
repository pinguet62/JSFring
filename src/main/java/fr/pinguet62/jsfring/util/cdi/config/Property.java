package fr.pinguet62.jsfring.util.cdi.config;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * Annotation used to define the property to inject.<br/>
 * The value is read from {@code .properties} files.
 */
@Qualifier
@Target({ TYPE, METHOD, FIELD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {

    /** The default value if not provided. */
    @Nonbinding
    String defaultValue() default "";

    /** The bundle key. */
    @Nonbinding
    String key() default "";

    // TODO mandatory

}