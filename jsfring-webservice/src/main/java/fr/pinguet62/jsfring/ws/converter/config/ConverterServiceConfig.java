package fr.pinguet62.jsfring.ws.converter.config;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;

import com.mysema.query.SearchResults;

import fr.pinguet62.jsfring.ws.dto.SearchResultsDto;

@Configuration
public class ConverterServiceConfig {

    /** @see ConversionService */
    public class TestConversionService extends DefaultConversionService {
        @Override
        protected GenericConverter getConverter(TypeDescriptor sourceType, TypeDescriptor targetType) {
            Class<?> srcClass = sourceType.getType();
            Class<?> tgtClass = targetType.getType();
            if (srcClass == SearchResults.class && tgtClass == SearchResultsDto.class)
                return new TestSearchResultsConverter();

            return super.getConverter(sourceType, targetType);
        }
    }

    public class TestSearchResultsConverter implements GenericConverter {
        @Override
        public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
            sourceType.getResolvableType().getGenerics();
            return convertTyped(source, sourceType, targetType);
        }

        private <Pojo, Dto> Object convertTyped(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
            TypeDescriptor srcType = TypeDescriptor.valueOf((Class) sourceType.getResolvableType().getGenerics()[0].getType());
            TypeDescriptor tgtType = TypeDescriptor.valueOf((Class) targetType.getResolvableType().getGenerics()[0].getType());
            Function<Pojo, Dto> mapper = x -> (Dto) converterService().convert(x, srcType, tgtType);

            SearchResults<Pojo> searchResults = (SearchResults<Pojo>) source;
            List<Dto> convertedResults = searchResults.getResults().stream().map(mapper).collect(toList());
            return new SearchResultsDto<Dto>(convertedResults, searchResults.getLimit(), searchResults.getOffset(),
                    searchResults.getTotal());
        }

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(new ConvertiblePair(SearchResults.class, SearchResultsDto.class));
        }
    }

    @Bean
    public GenericConversionService converterService() {
        // return new DefaultConversionService();
        return new TestConversionService();
    }

}