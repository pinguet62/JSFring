package fr.pinguet62.dictionary.filter;

/** Filter used for the pagination. */
// TODO null values tests
public final class PaginationFilter {

    /**
     * The index of first result.
     * <p>
     * The minimum is {@code 0} and can't be negative.
     */
    private Integer first;

    /** The number of result per page. */
    private Integer numberPerPage;

    public Integer getFirst() {
        return first;
    }

    /**
     * Get the index of first result.
     *
     * @return The first index.
     */
    public Integer getFirstIndex() {
        return first;
    }

    public Integer getNumberPerPage() {
        return numberPerPage;
    }

    /**
     * Set the first index..
     *
     * @param first
     *            The first index.
     * @throws IllegalArgumentException
     *             Negative value.
     */
    public void setFirst(Integer first) {
        if ((first != null) && (first < 0))
            throw new IllegalArgumentException("The index can't be negative.");

        this.first = first;
    }

    /**
     * Set the number of results per page.
     *
     * @param numberPerPage
     *            The number of results per page.
     * @throws IllegalArgumentException
     *             Negative value.
     */
    public void setNumberPerPage(Integer numberPerPage) {
        if ((numberPerPage != null) && (numberPerPage <= 0))
            throw new IllegalArgumentException("The number must be positive.");

        this.numberPerPage = numberPerPage;
    }

}
