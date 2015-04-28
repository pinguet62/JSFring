package fr.pinguet62.jsfring.gui;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

import fr.pinguet62.jsfring.gui.util.querydsl.converter.FilterConverter;
import fr.pinguet62.jsfring.gui.util.querydsl.converter.OrderConverter;
import fr.pinguet62.jsfring.model.QUser;
import fr.pinguet62.jsfring.service.AbstractService;

/**
 * Abstract {@link LazyDataModel} who implements default
 * {@link #load(int, int, String, SortOrder, Map) loading method} for
 * pagination.
 *
 * @param <T>
 *            The type of objects to display.
 */
public class AbstractLazyDataModel<T> extends LazyDataModel<T> {

    private static final long serialVersionUID = 1;

    /**
     * {@link AbstractManagedBean} with {@link AbstractManagedBean#getQuery()
     * query} used to load results.
     */
    private final AbstractManagedBean<T> managedBean;

    /**
     * @param managedBean
     *            {@link #managedBean}
     */
    public AbstractLazyDataModel(AbstractManagedBean<T> managedBean) {
        this.managedBean = managedBean;
    }

    /**
     * Load a <b>paginated</b> list of elements.
     * <p>
     * Get the current {@link AbstractManagedBean#getQuery() query} and add
     * paginated request to limit, filter & order results.
     * <p>
     * <u>Exemple:</u> for <code>pageSize=5</code>, the 3rd page will have
     * <code>first=10</code>.
     *
     * @param first
     *            Index of first element in current page to load.
     * @param pageSize
     *            The number of result per page.
     * @param sortField
     *            The field name on which filter.
     * @param sortOrder
     *            The order of sort.
     * @param filters
     *            Association of field names to value set by user to filter
     *            results.
     * @see AbstractService#findPanginated(JPAQuery)
     */
    @Override
    public List<T> load(int first, int pageSize, String sortField,
            SortOrder sortOrder, Map<String, Object> filters) {
        JPAQuery query = managedBean.getQuery();

        // Pagination
        query.offset(first);
        query.limit(pageSize);
        // Order
        if (sortField != null) {
            OrderSpecifier<?> order = new OrderConverter(QUser.user).apply(
                    sortField, sortOrder);
            if (order != null)
                query.orderBy(order);
        }
        // Filter
        BooleanExpression condition = new FilterConverter(QUser.user)
        .apply(filters);
        if (condition != null)
            query.where(condition);

        SearchResults<T> results = managedBean.getService().findPanginated(
                query);

        setRowCount((int) results.getTotal());
        return results.getResults();
    }

}