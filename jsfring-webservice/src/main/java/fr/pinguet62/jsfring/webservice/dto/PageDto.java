package fr.pinguet62.jsfring.webservice.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

/**
 * DTO for {@link Page paginated results}.
 *
 * @see Page
 */
@Data
public final class PageDto<T> {

    private List<T> results;

    private long total;

    public PageDto(List<T> results, long total) {
        this.results = results;
        this.total = total;
    }

}