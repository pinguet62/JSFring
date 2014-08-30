package fr.pinguet62.dictionary.filter;

import java.util.List;

public final class PaginationResult<T> {

    private final List<T> items;

    /** The number of result per page. */
    private final Integer numberPerPage = 5; // TODO delete me

    private final long totalCount;

    public PaginationResult(List<T> items, long totalCount) {
        this.items = items;
        this.totalCount = totalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public Integer getNumberPerPage() {
        return numberPerPage;
    }

    public long getTotalCount() {
        return totalCount;
    }

}
