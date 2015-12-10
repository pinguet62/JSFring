package fr.pinguet62.jsfring.ws.converter.config;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.mysema.query.SearchResults;

import fr.pinguet62.jsfring.Config;
import fr.pinguet62.jsfring.ws.dto.SearchResultsDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class ConversionServiceTest {

    @Inject
    private ConversionService conversionService;

    @Test
    public void test_SearchResultsConverter() {
        SearchResults<String> source = new SearchResults<>(Arrays.asList("1", "2"), 2l, 1l, 10l);

        TypeDescriptor srcType = new SearchResultsTypeDescriptor(String.class);
        TypeDescriptor tgtType = new SearchResultsDtoTypeDescriptor(Integer.class);

        Object target = conversionService.convert(source, srcType, tgtType);
        System.out.println(target);
        assertTrue(target instanceof SearchResultsDto);
    }

}