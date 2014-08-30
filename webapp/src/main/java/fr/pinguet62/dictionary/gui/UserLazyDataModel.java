package fr.pinguet62.dictionary.gui;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import fr.pinguet62.dictionary.filter.PaginationFilter;
import fr.pinguet62.dictionary.filter.PaginationResult;
import fr.pinguet62.dictionary.model.User;
import fr.pinguet62.dictionary.service.UserService;

public final class UserLazyDataModel extends LazyDataModel<User> {

    /** Serial version UID. */
    private static final long serialVersionUID = 2376788230677414872L;

    transient private UserService service;

    public UserLazyDataModel(UserService service) {
        this.service = service;
    }

    @Override
    public List<User> load(int first, int pageSize, String sortField,
            SortOrder sortOrder, Map<String, Object> filters) {
        PaginationFilter filter = new PaginationFilter();
        filter.setFirst(first);
        filter.setNumberPerPage(pageSize);
        PaginationResult<User> results = service.find(filter);

        List<User> users = results.getItems();
        setRowCount((int) results.getTotalCount());
        return users;
    }

}
