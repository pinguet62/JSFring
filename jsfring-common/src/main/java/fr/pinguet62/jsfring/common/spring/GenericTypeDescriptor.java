package fr.pinguet62.jsfring.common.spring;

import java.lang.annotation.Annotation;

import org.springframework.core.ResolvableType;
import org.springframework.core.convert.TypeDescriptor;

/**
 * {@link TypeDescriptor} with {@link ResolvableType} constructor.
 * <p>
 * Override {@code protected} constructor {@link TypeDescriptor#TypeDescriptor(ResolvableType, Class, Annotation[])}.
 */
public final class GenericTypeDescriptor extends TypeDescriptor {

    private static final long serialVersionUID = 1;

    public GenericTypeDescriptor(ResolvableType resolvableType) {
        super(resolvableType, null, null);
    }

}