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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysema.query.SearchResults;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.util.spring.GenericTypeDescriptor;
import fr.pinguet62.jsfring.ws.dto.SearchResultsDto;

/** @see SearchResultsGenericConverter */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
public class SearchResultsGenericConverterTest {

    @Inject
    private ConversionService conversionService;

    @Test
    public void test_sameType() {
        SearchResults<String> source = new SearchResults<>(asList("1", "2", "3"), 2l, 1l, 10l);

        TypeDescriptor srcType = new GenericTypeDescriptor(forClassWithGenerics(SearchResults.class, String.class));
        TypeDescriptor tgtType = new GenericTypeDescriptor(forClassWithGenerics(SearchResultsDto.class, String.class));
        Object converted = conversionService.convert(source, srcType, tgtType);

        // Converted type
        assertTrue(converted instanceof SearchResultsDto);
        SearchResultsDto<?> target = (SearchResultsDto<?>) converted;
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

        SearchResults<String> source = new SearchResults<>(asList("1", "2", "3"), 2l, 1l, 10l);

        TypeDescriptor srcType = new GenericTypeDescriptor(forClassWithGenerics(SearchResults.class, String.class));
        TypeDescriptor tgtType = new GenericTypeDescriptor(forClassWithGenerics(SearchResultsDto.class, Integer.class));
        Object converted = conversionService.convert(source, srcType, tgtType);

        // Converted type
        assertTrue(converted instanceof SearchResultsDto);
        SearchResultsDto<?> target = (SearchResultsDto<?>) converted;
        // Elements type
        assertTrue(target.getResults().get(0) instanceof Integer);
    }

    @Test(expected = ConversionFailedException.class)
    public void test_typeConverting_unknownConverter() {
        // Types
        class SrcType {}
        class TgtType {}
        assertFalse(conversionService.canConvert(SrcType.class, TgtType.class));

        SearchResults<SrcType> source = new SearchResults<>(asList(new SrcType(), new SrcType()), 2l, 1l, 10l);

        TypeDescriptor srcType = new GenericTypeDescriptor(forClassWithGenerics(SearchResults.class, SrcType.class));
        TypeDescriptor tgtType = new GenericTypeDescriptor(forClassWithGenerics(SearchResultsDto.class, TgtType.class));
        conversionService.convert(source, srcType, tgtType); // error
    }

}