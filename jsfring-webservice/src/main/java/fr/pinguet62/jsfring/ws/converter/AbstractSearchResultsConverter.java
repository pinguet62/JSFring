package fr.pinguet62.jsfring.ws.converter;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.mysema.query.SearchResults;

import fr.pinguet62.jsfring.ws.dto.SearchResultsDto;

/**
 * Abstract {@link Converter} used to convert {@link SearchResults} for POJO to
 * DTO.
 *
 * @param <Pojo> The POJO type.
 * @param <Dto> The DTO type.
 */
public abstract class AbstractSearchResultsConverter<Pojo, Dto>
        implements Converter<SearchResults<Pojo>, SearchResultsDto<Dto>> {

    @Override
    public SearchResultsDto<Dto> convert(SearchResults<Pojo> searchResults) {
        List<Dto> convertedResults = searchResults.getResults().stream().map(this::convertPojo).collect(toList());
        return new SearchResultsDto<Dto>(convertedResults, searchResults.getLimit(), searchResults.getOffset(),
                searchResults.getTotal());
    }

    protected abstract Dto convertPojo(Pojo pojo);

}