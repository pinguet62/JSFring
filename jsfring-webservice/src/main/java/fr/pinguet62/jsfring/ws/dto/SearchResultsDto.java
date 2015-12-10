package fr.pinguet62.jsfring.ws.dto;

import java.util.List;

import com.mysema.query.SearchResults;

/**
 * DTO for search results.
 *
 * @param <T> The result type.
 * @see SearchResults
 */
public final class SearchResultsDto<T> {

    public static <U> SearchResultsDto<U> New() {
        return new SearchResultsDto<U>(null, null, null, 0);
    }

    private final Long limit;

    private final Long offset;

    private final List<T> results;

    private final long total;

    public SearchResultsDto(List<T> results, Long limit, Long offset, long total) {
        this.results = results;
        this.limit = limit;
        this.offset = offset;
        this.total = total;
    }

    public Long getLimit() {
        return limit;
    }

    public Long getOffset() {
        return offset;
    }

    public List<T> getResults() {
        return results;
    }

    public long getTotal() {
        return total;
    }

}