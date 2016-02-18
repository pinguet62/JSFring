package fr.pinguet62.jsfring.gui;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.data.querydsl.SimpleEntityPathResolver.INSTANCE;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;

import fr.pinguet62.jsfring.service.AbstractService;

/**
 * Abstract class used to display results in {@link DataTable}.
 * <p>
 * Eager and lazy loading can be used simple by calling the corresponding method
 * into xHTML view.
 *
 * @param <T> The type of objects to display.
 * @see AbstractService#getAll()
 * @see AbstractService#findPanginated(JPAQuery)
 */
public abstract class AbstractBean<T extends Serializable> implements Serializable {

    private static final Logger LOGGER = getLogger(AbstractBean.class);

    private static final long serialVersionUID = 1;

    /** @property.getter {@link #getLazyDataModel()} */
    private final LazyDataModel<T> lazyDataModel = new AbstractLazyDataModel<T>(this);

    /**
     * Used for <b>eager loading</b> to store result of last call in database.
     */
    private Iterable<T> list;

    /** The {@link EntityPath} of target {@link Entity}. */
    @SuppressWarnings("unchecked")
    private final EntityPath<T> path = INSTANCE
            .createPath((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);

    /**
     * Used for lazy loading.<br>
     * <code>&lt;p:dataTable lazy="true" value="#{myBean.lazyDataModel}" ...&gt;</code>
     * <p>
     * The pagination is managed by the {@link AbstractLazyDataModel data model}
     * .
     *
     * @see AbstractLazyDataModel#load(int, int, String,
     *      org.primefaces.model.SortOrder, java.util.Map)
     * @property.attribute {@link #lazyDataModel}
     */
    public LazyDataModel<T> getLazyDataModel() {
        return lazyDataModel;
    }

    /**
     * Used for eager loading.<br>
     * <code>&lt;p:dataTable value="#{myBean.list}" ...&gt;</code>
     * <p>
     * Because the {@link DataTable} repeatedly calls this method, it's
     * necessary to avoid multiple call in database.<br>
     * So the {@link #list} is initialized at the first call (when is
     * {@code null}) and used by next calls.
     *
     * @see AbstractService#findAll(Predicate)
     */
    public Iterable<T> getList() {
        if (list == null) {
            LOGGER.debug("Eager loading: initialization");
            list = getService().findAll(getPredicate());
        }
        return list;
    }

    /**
     * The {@link OrderSpecifier} used to sort data.
     * <p>
     * To add custom order, override this method and {@link List#add(Object)
     * add} sort to {@code super} default implementation.
     *
     * @return The built {@link OrderSpecifier}.
     */
    protected List<OrderSpecifier<?>> getOrderSpecifiers() {
        return new ArrayList<>();
    }

    /**
     * Get the {@link EntityPath} of target {@link Entity}.
     *
     * @return The {@link #path}.
     */
    protected EntityPath<T> getPath() {
        return path;
    }

    /**
     * The {@link Predicate} used to filter data.
     * <p>
     * To add custom criteria, override this method and
     * {@link BooleanBuilder#and(Predicate) apply} criteria to {@code super}
     * default implementation.
     *
     * @return The built {@link Predicate}.
     */
    protected BooleanBuilder getPredicate() {
        return new BooleanBuilder();
    }

    /** Get the {@link AbstractService service} used to load data. */
    public abstract AbstractService<T, ? extends Serializable> getService();

    /**
     * Refresh the database.
     * <p>
     * After this call, next {@link #getList()} will initialize data from
     * database.
     */
    protected void refresh() {
        LOGGER.trace("Eager loading: refresh");
        list = null;
    }

}