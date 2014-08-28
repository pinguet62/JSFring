package fr.pinguet62.dictionary.filter;

import java.util.List;

public final class PaginationResult<T> {

    private final List<T> items;

    private final long totalCount;

    public PaginationResult(List<T> items, long totalCount) {
        this.items = items;
        this.totalCount = totalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public long getTotalCount() {
        return totalCount;
    }

}
