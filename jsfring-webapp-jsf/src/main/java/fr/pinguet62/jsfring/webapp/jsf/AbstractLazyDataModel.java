package fr.pinguet62.jsfring.webapp.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpressionBase;

import fr.pinguet62.jsfring.common.querydsl.FilterConverter;
import fr.pinguet62.jsfring.common.reflection.PropertyResolver;
import fr.pinguet62.jsfring.service.AbstractService;
import fr.pinguet62.jsfring.webapp.jsf.util.OrderConverter;

import static org.springframework.data.querydsl.QSort.unsorted;

// TODO Order: getOrderSpecifiers() not used
/**
 * Abstract {@link LazyDataModel} who implements default {@link #load(int, int, String, SortOrder, Map) loading method} for
 * lazy-loading and pagination.
 *
 * @param <T>
 *            The type of objects to display.
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
     * @param bean
     *            {@link #bean}
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
     * @param firstIndex
     *            Index of first element in current page to load.<br>
     *            Filter element (first page, first row) has an index of {@code 0}.
     * @param pageSize
     *            The number of result per page.
     * @param sortField
     *            The field name on which filter.<br>
     *            Default: {@code null}.
     * @param sortOrder
     *            The order of sort.<br>
     *            Default: {@link SortOrder#ASCENDING}.
     * @param filters
     *            Association of field names to value set by user to filter results.<br>
     *            Default: an empty {@link Map}.
     * @see AbstractService#findAll(Predicate, Pageable)
     */
    @Override
    public List<T> load(int firstIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        EntityPath<T> path = bean.getPath();

        // Pagination
        int pageIndex = firstIndex / pageSize;
        // Order
        QSort sort = unsorted();
        if (sortField != null) {
            ComparableExpressionBase<?> field = (ComparableExpressionBase<?>) new PropertyResolver(path).apply(sortField);
            Function<ComparableExpressionBase<?>, OrderSpecifier<?>> orderApplier = new OrderConverter().apply(sortOrder);
            OrderSpecifier<?> orderSpecifier = orderApplier.apply(field);
            sort = new QSort(orderSpecifier);
        }
        // Filter
        Predicate datatablePredicates = new FilterConverter(path).apply(filters);
        Predicate predicate = bean.getPredicate().and(datatablePredicates);

        // Request
        Pageable pageable = new QPageRequest(pageIndex, pageSize, sort);
        Page<T> page = bean.getService().findAll(predicate, pageable);

        // Save result state
        setRowCount((int) page.getTotalElements());
        return page.getContent();
    }

}