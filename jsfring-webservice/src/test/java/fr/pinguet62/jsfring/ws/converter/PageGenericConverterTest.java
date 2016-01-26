package fr.pinguet62.jsfring.ws.converter;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.core.ResolvableType.forClassWithGenerics;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.util.spring.GenericTypeDescriptor;
import fr.pinguet62.jsfring.ws.dto.PageDto;

/** @see PageGenericConverter */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
public class PageGenericConverterTest {

    @Inject
    private ConversionService conversionService;

    @Test
    public void test_sameType() {
        Page<String> source = new PageImpl<>(asList("1", "2", "3"), null, 10);

        TypeDescriptor srcType = new GenericTypeDescriptor(forClassWithGenerics(Page.class, String.class));
        TypeDescriptor tgtType = new GenericTypeDescriptor(forClassWithGenerics(PageDto.class, String.class));
        Object converted = conversionService.convert(source, srcType, tgtType);

        // Converted type
        assertTrue(converted instanceof PageDto);
        PageDto<?> target = (PageDto<?>) converted;
        // Elements type
        assertTrue(target.getResults().get(0) instanceof String);
    }

    /**
     * For the conversion from {@code Source<A>} to {@code Target<B>}, 2
     * {@link Converter} must be used:
     * <ul>
     * <li>From: {@code Source} to {@code Target}</li>
     * <li>From: {@code A} to {@code B}</li>
     * </ul>
     */
    @Test
    public void test_typeConverting() {
        assertTrue(conversionService.canConvert(String.class, Integer.class));

        Page<String> source = new PageImpl<>(asList("1", "2", "3"), null, 10);

        TypeDescriptor srcType = new GenericTypeDescriptor(forClassWithGenerics(Page.class, String.class));
        TypeDescriptor tgtType = new GenericTypeDescriptor(forClassWithGenerics(PageDto.class, Integer.class));
        Object converted = conversionService.convert(source, srcType, tgtType);

        // Converted type
        assertTrue(converted instanceof PageDto);
        PageDto<?> target = (PageDto<?>) converted;
        // Elements type
        assertTrue(target.getResults().get(0) instanceof Integer);
    }

    @Test(expected = ConversionFailedException.class)
    public void test_typeConverting_unknownConverter() {
        // Types
        class SrcType {}
        class TgtType {}
        assertFalse(conversionService.canConvert(SrcType.class, TgtType.class));

        Page<SrcType> source = new PageImpl<>(asList(new SrcType(), new SrcType()), null, 10);

        TypeDescriptor srcType = new GenericTypeDescriptor(forClassWithGenerics(Page.class, SrcType.class));
        TypeDescriptor tgtType = new GenericTypeDescriptor(forClassWithGenerics(PageDto.class, TgtType.class));
        conversionService.convert(source, srcType, tgtType); // error
    }

}