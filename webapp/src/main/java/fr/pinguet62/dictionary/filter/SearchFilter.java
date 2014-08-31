package fr.pinguet62.dictionary.filter;

import java.util.Map;

/**
 * Filter used to
 * <ul>
 * <li>pagination;</li>
 * <li>filter fields;</li>
 * <li>sort a field.</li>
 * </ul>
 */
public final class SearchFilter {

    /** Two types of order. */
    public static enum SortOrder {
        /** Default order. */
        ASCENDING, DESCENDING;
    }

    /**
     * Fields to filter.
     * <p>
     * Associate the field name to its value.
     */
    private Map<String, Object> fieldFilters;

    /**
     * The index of first result.
     * <p>
     * The minimum is {@code 0} and can't be negative.
     */
    private Integer first;

    /** The number of result per page. */
    private Integer numberPerPage;

    /** The field to order. */
    private String sortField;

    /** The order for {@link #sortField}. */
    private SortOrder sortOrder;

    /**
     * Get the fields to filter.
     *
     * @return The fields to filter.
     */
    public Map<String, Object> getFieldFilters() {
        return fieldFilters;
    }

    /**
     * Get the index of first result.
     *
     * @return The first index.
     */
    public Integer getFirstIndex() {
        return first;
    }

    /**
     * Get the number of results per page.
     *
     * @return The number.
     */
    public Integer getNumberPerPage() {
        return numberPerPage;
    }

    /**
     *
     * @return
     * @author Julien
     */
    public String getSortField() {
        return sortField;
    }

    /**
     * Get the sort order.
     *
     * @return The order.
     */
    public SortOrder getSortOrder() {
        return sortOrder;
    }

    /**
     * Set the fields to filter.
     * <p>
     * An empty {@link Map} or {@code null} reset the filter.
     *
     * @param fieldFilters
     *            The association of field name to the value.
     */
    public void setFieldFilters(Map<String, Object> fieldFilters) {
        this.fieldFilters = fieldFilters;
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

    /**
     * Set the field to order.
     *
     * @param sortField
     *            The field.
     */
    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    /**
     * Set the sort order.
     *
     * @param sortOrder
     *            The order.
     */
    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

}
