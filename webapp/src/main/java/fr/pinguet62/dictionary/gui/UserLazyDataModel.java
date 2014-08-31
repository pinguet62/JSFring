package fr.pinguet62.dictionary.gui;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import fr.pinguet62.dictionary.filter.PaginationResult;
import fr.pinguet62.dictionary.filter.SearchFilter;
import fr.pinguet62.dictionary.model.User;
import fr.pinguet62.dictionary.service.AbstractService;
import fr.pinguet62.dictionary.service.UserService;

public final class UserLazyDataModel extends LazyDataModel<User> {

    /** Serial version UID. */
    private static final long serialVersionUID = 2376788230677414872L;

    /**
     * Convert {@link SortOrder} to {@link SearchFilter.SortOrder}
     *
     * @param primeOrder
     *            The PrimeFaces {@link SortOrder}.
     * @return The {@link SearchFilter.SortOrder}.
     * @throws IllegalArgumentException
     *             Unknown value.
     */
    private static final SearchFilter.SortOrder convert(SortOrder primeOrder) {
        if (primeOrder == null)
            return null;

        switch (primeOrder) {
            case ASCENDING:
            case UNSORTED:
                return SearchFilter.SortOrder.ASCENDING;
            case DESCENDING:
                return SearchFilter.SortOrder.DESCENDING;
            default:
                throw new IllegalArgumentException(SortOrder.class.getName()
                        + " unknown: " + primeOrder);
        }
    }

    /** The {@link AbstractService}. */
    transient private UserService service;

    /**
     * Constructor.
     *
     * @param service
     *            The {@link AbstractService}.
     */
    public UserLazyDataModel(UserService service) {
        this.service = service;
    }

    @Override
    public List<User> load(int first, int pageSize, String sortField,
            SortOrder sortOrder, Map<String, Object> filters) {
        SearchFilter filter = new SearchFilter();
        filter.setFirst(first);
        filter.setNumberPerPage(pageSize);
        filter.setSortField(sortField);
        filter.setSortOrder(convert(sortOrder));
        filter.setFieldFilters(filters);
        PaginationResult<User> results = service.find(filter);

        List<User> users = results.getItems();
        setRowCount((int) results.getTotalCount());
        return users;
    }

}
