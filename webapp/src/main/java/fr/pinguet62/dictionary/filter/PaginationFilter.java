package fr.pinguet62.dictionary.filter;

/** Filter used for the pagination. */
// TODO null values tests
public final class PaginationFilter {

    /** The number of result per page. */
    private Integer numberPerPage;

    /**
     * The current page.
     * <p>
     * The first page is {@code 1} and can't be negative.
     */
    private Integer page;

    /**
     * Check if the two parameters {@link #page} and {@link #numberPerPage} are
     * set.
     *
     * @throws FilterException
     *             If {@link #page} or {@link #numberPerPage} is not set.
     */
    private void checkParameters() {
        if (page == null)
            throw new FilterException("The page is not set.");
        if (numberPerPage == null)
            throw new FilterException("The number per page is not set.");
    }

    /**
     * Get the index of first result.
     * <p>
     * Calculated from {@link #page} and {@link #numberPerPage}.
     *
     * @return The first index.
     * @throws FilterException
     *             If {@link #page} or {@link #numberPerPage} is not set.
     */
    public int getFirstIndex() {
        checkParameters();
        return (page - 1) * numberPerPage;
    }

    /**
     * Get the index of last result.
     * <p>
     * Calculated from {@link #page} and {@link #numberPerPage}.
     *
     * @return The last index.
     * @throws FilterException
     *             If {@link #page} or {@link #numberPerPage} is not set.
     */
    public int getLastIndex() {
        checkParameters();
        return page * numberPerPage;
    }

    public Integer getNumberPerPage() {
        return numberPerPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setNumberPerPage(Integer numberPerPage) {
        this.numberPerPage = numberPerPage;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

}
