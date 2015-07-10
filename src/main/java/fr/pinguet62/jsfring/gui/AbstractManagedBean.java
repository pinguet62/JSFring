package fr.pinguet62.jsfring.gui;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.jsfring.service.AbstractService;

/**
 * Abstract {@link ManagedBean} with default method to display results in
 * {@link DataTable}.
 *
 * @param <T> The type of objects to display.
 */
public abstract class AbstractManagedBean<T> implements Serializable {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AbstractManagedBean.class);

    private static final long serialVersionUID = 1;

    /** @property.getter {@link #getLazyDataModel()} */
    private final LazyDataModel<T> lazyDataModel = new AbstractLazyDataModel<T>(
            this);

    /** Used for <b>eager loading</b> to store result of last call in database. */
    private List<T> list;

    /**
     * Used for lazy loading.<br/>
     * <code>p:dataTable lazy="true" value="#{managedBean.lazyDataModel}" ...></code>
     * <p>
     * The pagination is managed by the {@link AbstractLazyDataModel data model}.
     *
     * @see AbstractLazyDataModel#load(int, int, String,
     *      org.primefaces.model.SortOrder, java.util.Map)
     * @property.attribute {@link #lazyDataModel}
     */
    public LazyDataModel<T> getLazyDataModel() {
        return lazyDataModel;
    }

    /**
     * Used for eager loading.<br/>
     * <code>&lt;p:dataTable value="#{managedBean.list}" ...&gt;</code>
     * <p>
     * Because the {@link DataTable} repeatedly calls this method, it's
     * necessary to avoid multiple call in database.<br/>
     * So the {@link #list} is initialized at the first call (when is
     * {@code null}) and used by next calls.
     *
     * @see AbstractService#find(JPAQuery)
     */
    public List<T> getList() {
        if (list == null) {
            LOGGER.debug("Eager loading: initialization");
            list = getService().find(getQuery());
        }
        return list;
    }

    /**
     * Get the {@link JPAQuery query} to get data.
     * <p>
     * Don't add the paginated filters: they will be added automatically by
     * {@link AbstractLazyDataModel lazy data-model} during
     * {@link AbstractLazyDataModel#load(int, int, String, org.primefaces.model.SortOrder, java.util.Map)
     * loading}.
     */
    abstract protected JPAQuery getQuery();

    /** Get the {@link AbstractService service} used to load data. */
    abstract public AbstractService<T, ?> getService();

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