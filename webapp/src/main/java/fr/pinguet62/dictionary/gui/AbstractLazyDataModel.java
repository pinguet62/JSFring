package fr.pinguet62.dictionary.gui;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

import fr.pinguet62.dictionary.model.QUser;
import fr.pinguet62.dictionary.service.AbstractService;
import fr.pinguet62.util.querydsl.Filter;
import fr.pinguet62.util.querydsl.Order;
import fr.pinguet62.util.querydsl.ReflectionUtil;

/**
 * Add methods to {@link LazyDataModel} for lazy loading: filtering and sorting.
 *
 * @param <T>
 *            The target type.
 */
public class AbstractLazyDataModel<T> extends LazyDataModel<T> {

    /** Serial version UID. */
    private static final long serialVersionUID = 1;

    /** The {@link EntityPath}. */
    private final EntityPath<?> defaultMetaObject;

    /** The {@link AbstractService}. */
    transient protected final AbstractService<T, ?> service;

    /**
     * Constructor. <br/>
     * Used by sub classes who the target type is set.
     *
     * @param service
     *            The {@link AbstractService}.
     */
    @SuppressWarnings("unchecked")
    protected AbstractLazyDataModel(AbstractService<T, ?> service) {
        this.service = service;

        Class<T> entityType = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        defaultMetaObject = ReflectionUtil.getDefaultMetaObject(entityType);
    }

    /**
     * Constructor with {@link Entity} type.<br/>
     * Used when this class is directly used without extending.
     *
     * @param service
     *            The {@link AbstractService}.
     * @param entityType
     *            The {@link Entity} type.
     */
    public AbstractLazyDataModel(AbstractService<T, ?> service,
            Class<T> entityType) {
        this.service = service;
        defaultMetaObject = ReflectionUtil.getDefaultMetaObject(entityType);
    }

    /**
     * Load the lazy {@link DataTable}.
     *
     * @param first
     *            The index of first element of current page.<br/>
     *            For example, if {@code pageSize=5} and the current page is the
     *            3rd, the {@code first} will be 10.
     * @param pageSize
     *            The number of item per page.
     * @param sortField
     *            The property of sorted field (relative to base object).
     * @param sortOrder
     *            The {@link SortOrder}.
     * @param filters
     *            The association property/value. An empty {@link Map} if no
     *            filter applied.
     */
    @Override
    public List<T> load(int first, int pageSize, String sortField,
            SortOrder sortOrder, Map<String, Object> filters) {
        JPAQuery query = new JPAQuery();
        query.from(defaultMetaObject);

        // Pagination
        query.offset(first);
        query.limit(pageSize);
        // Order
        if (sortField != null) {
            OrderSpecifier<?> order = new Order(QUser.user).apply(sortField,
                    sortOrder);
            if (order != null)
                query.orderBy(order);
        }
        // Filter
        BooleanExpression condition = new Filter(QUser.user).apply(filters);
        if (condition != null)
            query.where(condition);

        SearchResults<T> results = service.findPanginated(query);

        setRowCount((int) results.getTotal());
        return results.getResults();
    }

}
