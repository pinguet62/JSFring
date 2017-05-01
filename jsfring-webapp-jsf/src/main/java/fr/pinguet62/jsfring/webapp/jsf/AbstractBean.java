package fr.pinguet62.jsfring.webapp.jsf;

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
import org.springframework.data.domain.Pageable;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

import fr.pinguet62.jsfring.service.AbstractService;

/**
 * Abstract class used to display results in {@link DataTable}.
 * <p>
 * Eager and lazy loading can be used simple by calling the corresponding method into xHTML view.
 *
 * @param <T>
 *            The type of objects to display.
 */
public abstract class AbstractBean<T extends Serializable> implements Serializable {

    private static final Logger LOGGER = getLogger(AbstractBean.class);

    private static final long serialVersionUID = 1;

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
     * Used for <b>lazy loading</b>.<br>
     * <code>&lt;p:dataTable lazy="true" value="#{myBean.lazyDataModel}" ...&gt;</code>
     * <p>
     * The pagination is managed by the {@link AbstractLazyDataModel data model} .
     *
     * @return {@link #lazyDataModel}
     * @see AbstractLazyDataModel#load(int, int, String, org.primefaces.model.SortOrder, java.util.Map)
     */
    public LazyDataModel<T> getLazyDataModel() {
        return lazyDataModel;
    }

    /**
     * Used for <b>eager loading</b>.<br>
     * <code>&lt;p:dataTable value="#{myBean.list}" ...&gt;</code>
     * <p>
     * <u>Cache:</u> because the {@link DataTable} repeatedly calls this method, the value is stored on {@link #list attribute}
     * to avoid multiple call in database.<br>
     * The {@link #list value} is initialized at the first call (when is {@code null}) and reused by next calls.
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
     * <u>Default:</u> No order.<br>
     * <u>Custom:</u> {@link Override} this method and {@link List#add(Object) add} {@link OrderSpecifier} to {@code super}
     * default implementation.
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
     * To add custom criteria, override this method and {@link BooleanBuilder#and(Predicate) apply} criteria to {@code super}
     * default implementation.
     *
     * @return The built {@link Predicate}.
     */
    protected BooleanBuilder getPredicate() {
        return new BooleanBuilder();
    }

    /**
     * Get the {@link AbstractService service} used to load data.
     *
     * @return The {@link AbstractService}.
     * @see AbstractService#getAll()
     * @see AbstractService#findAll(Predicate, Pageable)
     */
    public abstract AbstractService<T, ? extends Serializable> getService();

    /** Refresh the content, by reseting cached {@link #list}. */
    protected void refresh() {
        LOGGER.trace("Eager loading: refresh");
        list = null;
    }

}