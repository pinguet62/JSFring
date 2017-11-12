package fr.pinguet62.jsfring.common.spring;

import org.junit.Test;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

import static org.springframework.core.ResolvableType.forClass;
import static org.springframework.core.ResolvableType.forClassWithGenerics;

/**
 * @see GenericTypeDescriptor
 */
public final class GenericTypeDescriptorTest {

    /**
     * Check compilation.
     * <p>
     * {@code Map<String, Converter<Integer, Number>>}
     */
    @Test
    public void test_multiGenericTypes() {
        ResolvableType resolvableType = forClassWithGenerics(
                Map.class,
                forClass(String.class),
                forClassWithGenerics(Converter.class, Integer.class, Number.class)
        );
        new GenericTypeDescriptor(resolvableType);
    }

}