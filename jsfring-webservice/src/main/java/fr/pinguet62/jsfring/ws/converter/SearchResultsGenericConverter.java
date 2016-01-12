package fr.pinguet62.jsfring.ws.converter;

import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;
import static org.springframework.core.convert.TypeDescriptor.valueOf;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import javax.inject.Inject;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.stereotype.Component;

import com.mysema.query.SearchResults;

import fr.pinguet62.jsfring.ws.dto.SearchResultsDto;

/**
 * {@link GenericConverter} from {@link SearchResults} to
 * {@link SearchResultsDto}.
 * <p>
 * Each value of {@link SearchResults#getResults() results} are converted using
 * corresponding converter.<br>
 * For example, to convert {@code SearchResults<String>} to
 * {@code SearchResultsDto<Integer>}, the {@code Converter<String, Integer>}
 * must exists.
 */
@Component
public class SearchResultsGenericConverter implements GenericConverter {

    @Inject
    private GenericConversionService conversionService;

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return convertTyped(source, sourceType, targetType);
    }

    /** Generic method used to cast objects to correct type. */
    private <Pojo, Dto> Object convertTyped(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        TypeDescriptor srcType = valueOf((Class<?>) sourceType.getResolvableType().getGenerics()[0].getType());
        TypeDescriptor tgtType = valueOf((Class<?>) targetType.getResolvableType().getGenerics()[0].getType());
        Function<Pojo, Dto> mapper = x -> (Dto) conversionService.convert(x, srcType, tgtType);

        SearchResults<Pojo> searchResults = (SearchResults<Pojo>) source;
        List<Dto> convertedResults = searchResults.getResults().stream().map(mapper).collect(toList());
        return new SearchResultsDto<Dto>(convertedResults, searchResults.getLimit(), searchResults.getOffset(),
                searchResults.getTotal());
    }

    /**
     * Define source and target {@link Class} of {@link Converter}.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return singleton(new ConvertiblePair(SearchResults.class, SearchResultsDto.class));
    }

}