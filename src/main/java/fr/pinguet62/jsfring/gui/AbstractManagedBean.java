package fr.pinguet62.jsfring.gui;

import java.io.Serializable;
import java.util.List;

import org.primefaces.model.LazyDataModel;

import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.jsfring.service.AbstractService;

public abstract class AbstractManagedBean<T> implements Serializable {

    private static final long serialVersionUID = 1;

    private final LazyDataModel<T> lazyDataModel = new AbstractLazyDataModel<T>(
            this);

    /**
     * Used for lazy loading.
     *
     * @see AbstractService#findPanginated(JPAQuery)
     */
    public LazyDataModel<T> getLazyDataModel() {
        return lazyDataModel;
    }

    /**
     * Used for eager loading.
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
     * {@link AbstractLazyDataModel lazy data model} during loading.
     */
    abstract protected JPAQuery getQuery();

    abstract public AbstractService<T, ?> getService();

}
