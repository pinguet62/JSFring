package fr.pinguet62.jsfring.gui;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.EntityPathBase;

import fr.pinguet62.jsfring.gui.util.querydsl.converter.FilterConverter;
import fr.pinguet62.jsfring.gui.util.querydsl.converter.OrderConverter;
import fr.pinguet62.jsfring.service.AbstractService;

/**
 * Abstract {@link LazyDataModel} who implements default
 * {@link #load(int, int, String, SortOrder, Map) loading method} for
 * lazy-loading and pagination.
 *
 * @param <T> The type of objects to display.
 */
public class AbstractLazyDataModel<T extends Serializable> extends
LazyDataModel<T> {

    private static final long serialVersionUID = 1;

    /**
     * {@link AbstractBean} with {@link AbstractBean#getQuery() query} used to
     * load results.
     */
    private final AbstractBean<T> bean;

    /**
     * @param bean {@link #bean}
     */
    public AbstractLazyDataModel(AbstractBean<T> bean) {
        this.bean = bean;
    }

    /**
     * Load a paginated list of elements.
     * <p>
     * Get the current {@link AbstractBean#getQuery() query} and add paginated
     * request to limit, filter & order results. <br>
     * <u>Exemple:</u> for <code>pageSize=5</code>, the 3rd page will have
     * <code>first=10</code>.
     *
     * @param first Index of first element in current page to load.
     * @param pageSize The number of result per page.
     * @param sortField The field name on which filter.
     * @param sortOrder The order of sort.
     * @param filters Association of field names to value set by user to filter
     *            results.
     * @see AbstractService#findPanginated(JPAQuery)
     */
    @Override
    public List<T> load(int first, int pageSize, String sortField,
            SortOrder sortOrder, Map<String, Object> filters) {
        EntityPathBase<T> from = bean.getBaseExpression();
        JPAQuery query = bean.getQuery();

        // Pagination
        query.offset(first);
        query.limit(pageSize);
        // Order
        if (sortField != null) {
            OrderSpecifier<?> order = new OrderConverter(from).apply(sortField,
                    sortOrder);
            if (order != null)
                query.orderBy(order);
        }
        // Filter
        Predicate predicate = new FilterConverter(from).apply(filters);
        query.where(predicate);

        SearchResults<T> results = bean.getService().findPanginated(query);

        setRowCount((int) results.getTotal());
        return results.getResults();
    }

}