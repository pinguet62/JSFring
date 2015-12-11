package fr.pinguet62.jsfring.ws.converter.config;

import org.springframework.core.ResolvableType;
import org.springframework.core.convert.TypeDescriptor;

/**
 * Utilities methods for {@link TypeDescriptor}.
 * 
 * @see TypeDescriptor
 */
public final class TypeDescriptorUtils {

    // TODO Check if this class exists into Spring framework
    private static final class GenericTypeDescriptor extends TypeDescriptor {
        private static final long serialVersionUID = 1;

        GenericTypeDescriptor(Class<?> classType, Class<?>... argumentTypes) {
            super(ResolvableType.forClassWithGenerics(classType, argumentTypes), null, null);
        }
    }

    public static TypeDescriptor generic(Class<?> classType, Class<?>... argumentTypes) {
        return new GenericTypeDescriptor(classType, argumentTypes);
    }

    // Utils
    private TypeDescriptorUtils() {}

}