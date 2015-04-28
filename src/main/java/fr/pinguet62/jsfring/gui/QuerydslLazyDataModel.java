package fr.pinguet62.jsfring.gui;

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

import fr.pinguet62.jsfring.gui.util.querydsl.ReflectionUtil;
import fr.pinguet62.jsfring.gui.util.querydsl.converter.FilterConverter;
import fr.pinguet62.jsfring.gui.util.querydsl.converter.OrderConverter;
import fr.pinguet62.jsfring.model.QUser;
import fr.pinguet62.jsfring.service.AbstractService;

/**
 * {@link LazyDataModel} using the Querydsl API for sorting and sorting.
 * <p>
 * The methods of {@link Entity}s must respect the default property naming:
 * {@code get} + the name of attribute. For example {@code getName()} for the
 * attribute {@code name}.
 *
 * @param <T>
 *            The base type.
 */
public class QuerydslLazyDataModel<T> extends LazyDataModel<T> {

    private static final long serialVersionUID = 1;

    /** The default meta-object. */
    private final EntityPath<T> defaultMetaObject;

    /** The {@link AbstractService}. */
    transient protected final AbstractService<T, ?> service;

    /**
     * Constructor.
     * <p>
     * Used by sub classes who extends this class, and on which the target type
     * is set. For example:
     *
     * <pre>
     * class UserLazyDataModel extends QuerydslLazyDataModel&lt;User&gt; {
     *     UserLazyDataModel(AbstractService&lt;T, ?&gt; service) {
     *         super(service);
     *     }
     * }
     * </pre>
     *
     * @param service
     *            The {@link AbstractService}.
     */
    @SuppressWarnings("unchecked")
    protected QuerydslLazyDataModel(AbstractService<T, ?> service) {
        this.service = service;

        Class<T> entityType = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        defaultMetaObject = ReflectionUtil.getDefaultMetaObject(entityType);
    }

    /**
     * Constructor with {@link Entity} type.
     * <p>
     * Used when this class is directly used without extending. For example:<br>
     * {@code new QuerydslLazyDataModel(service, QUser.user)}
     *
     * @param service
     *            The {@link AbstractService}.
     * @param defaultMetaObject
     *            The default meta-object.
     */
    public QuerydslLazyDataModel(AbstractService<T, ?> service,
            EntityPath<T> defaultMetaObject) {
        this.service = service;
        this.defaultMetaObject = defaultMetaObject;
    }

    /**
     * Load the lazy {@link DataTable}.
     *
     * @param first
     *            The index of first element of current page. The minimum is
     *            {@code 0}.<br>
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

        SearchResults<T> results = service.findPanginated(query);

        setRowCount((int) results.getTotal());
        return results.getResults();
    }

}
