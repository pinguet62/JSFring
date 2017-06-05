package fr.pinguet62.jsfring.webservice.converter;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.core.ResolvableType.forClassWithGenerics;
import static org.springframework.data.domain.Pageable.unpaged;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.common.spring.GenericTypeDescriptor;
import fr.pinguet62.jsfring.webservice.dto.PageDto;

/** @see PageGenericConverter */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class, webEnvironment = DEFINED_PORT)
public class PageGenericConverterTest {

    @Inject
    private ConversionService conversionService;

    @Test
    public void test_sameType() {
        Page<String> source = new PageImpl<>(asList("1", "2", "3"), unpaged(), 10);

        TypeDescriptor srcType = new GenericTypeDescriptor(forClassWithGenerics(Page.class, String.class));
        TypeDescriptor tgtType = new GenericTypeDescriptor(forClassWithGenerics(PageDto.class, String.class));
        Object converted = conversionService.convert(source, srcType, tgtType);

        // Converted type
        assertThat(converted, is(instanceOf(PageDto.class)));
        PageDto<?> target = (PageDto<?>) converted;
        // Elements type
        assertThat(target.getResults().get(0), is(instanceOf(String.class)));
    }

    /**
     * For the conversion from {@code Source<A>} to {@code Target<B>}, 2 {@link Converter} must be used:
     * <ul>
     * <li>From: {@code Source} to {@code Target}</li>
     * <li>From: {@code A} to {@code B}</li>
     * </ul>
     */
    @Test
    public void test_typeConverting() {
        assertThat(conversionService.canConvert(String.class, Integer.class), is(true));

        Page<String> source = new PageImpl<>(asList("1", "2", "3"), unpaged(), 10);

        TypeDescriptor srcType = new GenericTypeDescriptor(forClassWithGenerics(Page.class, String.class));
        TypeDescriptor tgtType = new GenericTypeDescriptor(forClassWithGenerics(PageDto.class, Integer.class));
        Object converted = conversionService.convert(source, srcType, tgtType);

        // Converted type
        assertThat(converted, is(instanceOf(PageDto.class)));
        PageDto<?> target = (PageDto<?>) converted;
        // Elements type
        assertThat(target.getResults().get(0), is(instanceOf(Integer.class)));
    }

    @Test(expected = ConversionFailedException.class)
    public void test_typeConverting_unknownConverter() {
        // Types
        class SrcType {
        }
        class TgtType {
        }
        assertThat(conversionService.canConvert(SrcType.class, TgtType.class), is(false));

        Page<SrcType> source = new PageImpl<>(asList(new SrcType(), new SrcType()), unpaged(), 10);

        TypeDescriptor srcType = new GenericTypeDescriptor(forClassWithGenerics(Page.class, SrcType.class));
        TypeDescriptor tgtType = new GenericTypeDescriptor(forClassWithGenerics(PageDto.class, TgtType.class));
        conversionService.convert(source, srcType, tgtType); // error
    }

}