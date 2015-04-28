package fr.pinguet62.jsfring.gui;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;

import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.jsfring.service.AbstractService;

/**
 * Abstract {@link ManagedBean} with default method to display results in
 * {@link DataTable}.
 *
 * @param <T>
 *            The type of objects to display.
 */
public abstract class AbstractManagedBean<T> implements Serializable {

    private static final long serialVersionUID = 1;

    /** @property.getter {@link #getLazyDataModel()} */
    private final LazyDataModel<T> lazyDataModel = new AbstractLazyDataModel<T>(
            this);

    /**
     * Used for lazy loading.
     * <p>
     * The pagination is managed by the {@link AbstractLazyDataModel data model}.
     * <p>
     * <code>p:dataTable lazy="true" value="#{managedBean.lazyDataModel}" ...></code>
     *
     * @see AbstractLazyDataModel#load(int, int, String,
     *      org.primefaces.model.SortOrder, java.util.Map)
     * @property.attribute {@link #lazyDataModel}
     */
    public LazyDataModel<T> getLazyDataModel() {
        return lazyDataModel;
    }

    /**
     * Used for eager loading.
     * <p>
     * TODO crochet <code>p:dataTable value="#{managedBean.list}" ...></code>
     *
     * @see AbstractService#find(JPAQuery)
     */
    public List<T> getList() {
        return getService().find(getQuery());
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

}