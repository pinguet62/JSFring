package fr.pinguet62.jsfring.ws.converter.config;

import static org.springframework.core.ResolvableType.forClass;
import static org.springframework.core.ResolvableType.forClassWithGenerics;

import java.util.Map;

import org.junit.Test;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.converter.Converter;

/** @see GenericTypeDescriptorTest */
public final class GenericTypeDescriptorTest {

    /** {@code Map<String, Converter<Integer, Number>>} */
    @Test
    public void test_multiGenericTypes() {
        ResolvableType resolvableType = forClassWithGenerics(Map.class, forClass(String.class),
                forClassWithGenerics(Converter.class, Integer.class, Number.class));
        new GenericTypeDescriptor(resolvableType);
    }

}