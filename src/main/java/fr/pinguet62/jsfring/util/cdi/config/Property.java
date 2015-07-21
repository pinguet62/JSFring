package fr.pinguet62.jsfring.util.cdi.config;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * Annotation used to define the property to inject.<br>
 * The value is read from {@code .properties} files.
 */
@Qualifier
@Target({ FIELD, METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {

    /**
     * The default value.<br>
     * If the property is {@link #requiered() requiered} then this default value
     * will not be used.
     */
    @Nonbinding
    String defaultValue() default "";

    /** The property key. */
    @Nonbinding
    String key() default "";

    /**
     * If the property must exist.<br>
     * If the key doesn't exist, a {@link IllegalStateException} will be thrown.
     */
    boolean requiered() default false;

}