package fr.pinguet62.jsfring.webapp.jsf;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import fr.pinguet62.jsfring.common.querydsl.FilterConverter;
import fr.pinguet62.jsfring.common.querydsl.SortConverter;
import fr.pinguet62.jsfring.service.AbstractService;
import fr.pinguet62.jsfring.webapp.jsf.util.PrimefacesSortOrderConverter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

// TODO Order: getOrderSpecifiers() not used

/**
 * Abstract {@link LazyDataModel} who implements default {@link #load(int, int, String, SortOrder, Map) loading method} for
 * lazy-loading and pagination.
 *
 * @param <T> The type of objects to display.
 */
public class AbstractLazyDataModel<T extends Serializable> extends LazyDataModel<T> {

    private static final long serialVersionUID = 1;

    /**
     * {@link AbstractBean} having base query used to load data.
     *
     * @see AbstractBean#getPath()
     * @see AbstractBean#getPredicate()
     * @see AbstractBean#getOrderSpecifiers()
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
     * Get the current {@link AbstractBean#getPredicate() predicates} and {@link AbstractBean#getOrderSpecifiers() orders}, then
     * add pagination and datatable filter and sorting.
     * <p>
     * For filtering, the field name doesn't contains the object name on with EL expression will be resolved. For example if the
     * EL expression in xHTML is <code>"#&#123;right.code&#125;"</code>, so the field name will be {@code "code"}.
     *
     * @param firstIndex Index of first element in current page to load.<br>
     *                   Filter element (first page, first row) has an index of {@code 0}.
     * @param pageSize   The number of result per page.
     *                   {@link Pageable#getPageSize()}
     * @param sortField  The {@link ComparableExpressionBase path} on which sort, relative to {@code path}.
     * @param sortOrder  The order of sort.<br>
     *                   Default: {@link SortOrder#ASCENDING}.
     * @param filters    Association of field names to value set by user to filter results.<br>
     *                   Default: an empty {@link Map}.
     * @see AbstractService#findAll(Predicate, Pageable)
     */
    @Override
    public List<T> load(
            int firstIndex, int pageSize,
            String sortField, SortOrder sortOrder,
            Map<String, Object> filters
    ) {
        EntityPath<T> path = bean.getPath();

        // Order
        Direction direction = new PrimefacesSortOrderConverter().apply(sortOrder);
        QSort sort = new SortConverter(path).apply(sortField, direction);

        // Filter
        Predicate datatablePredicates = new FilterConverter(path).apply(filters);
        Predicate predicate = bean.getPredicate().and(datatablePredicates);

        // Pagination
        int pageIndex = firstIndex / pageSize;

        // Request
        Pageable pageable = new QPageRequest(pageIndex, pageSize, sort);
        Page<T> page = bean.getService().findAll(predicate, pageable).block();

        // Save result state
        setRowCount((int) page.getTotalElements());
        return page.getContent();
    }

}