package fr.pinguet62.jsfring.webserivce.dto;

import java.util.List;

import org.springframework.data.domain.Page;

/**
 * DTO for {@link Page paginated results}.
 *
 * @see Page
 */
public final class PageDto<T> {

    private List<T> results;

    private long total;

    public PageDto(List<T> results, long total) {
        this.results = results;
        this.total = total;
    }

    public List<T> getResults() {
        return results;
    }

    public long getTotal() {
        return total;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public void setTotal(long total) {
        this.total = total;
    }

}