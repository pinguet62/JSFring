package fr.pinguet62.jsfring.gui;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pinguet62.jsfring.service.AbstractService;

/**
 * {@link AbstractSelectableBean} with CRUD operations on selected value.
 * <p>
 * Before each action, the
 * {@link AbstractSelectableBean#setSelectedValue(Serializable) selected value}
 * is initialized with chosen value, and action are performed on him.
 *
 * @see AbstractService#create(Serializable)
 * @see AbstractService#get(Serializable)
 * @see AbstractService#update(Serializable)
 * @see AbstractService#delete(Serializable)
 */
public abstract class AbstractCrudBean<T extends Serializable> extends AbstractSelectableBean<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCrudBean.class);

    private static final long serialVersionUID = 1;

    /**
     * Create new value.<br>
     * Call when the user click on <i>Submit</i> button into <i>Create view</i>.
     * <p>
     * The initial {@link #getSelectedValue() selected value} is the
     * {@link #getNewValue() new value} {@link #setSelectedValue(Serializable)
     * set} by calling {@link #preCreate()} before showing the <i>create
     * view</i>.
     * <p>
     * {@link #refresh() Refresh} list after creation.
     *
     * @see #preCreate()
     * @see AbstractService#create(Serializable)
     */
    public void create() {
        try {
            getService().create(getSelectedValue());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "New element created with success", null));
            refresh();
        } catch (RuntimeException e) {
            LOGGER.warn("Error during creation", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error during creation", null));
        }
    }

    /**
     * Delete the selected value.
     * <p>
     * {@link #refresh() Refresh} list after deletion.
     *
     * @see #getSelectedValue()
     * @see AbstractService#delete(Serializable)
     */
    public void delete() {
        try {
            getService().delete(getSelectedValue());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Element deleted with success", null));
            postDelete();
            refresh();
        } catch (RuntimeException e) {
            LOGGER.warn("Error during deletion", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error during deletion", null));
        }
    }

    /**
     * Get a new instance of the value, for the creation.
     *
     * @see #create()
     */
    abstract protected T getNewValue();

    /**
     * <b>Fix {@link LazyDataModel} count after deletion:</b><br>
     * For lazy loading, after deletion of only row of last page, the page is
     * empty because {@link DataTable} thinks the current page is the same. So
     * the {@link LazyDataModel#getRowCount() row count} is decremented to
     * permit the return to the previous page.
     *
     * @see LazyDataModel#setRowCount(int)
     */
    protected void postDelete() {
        LazyDataModel<T> lazyDataModel = getLazyDataModel();
        lazyDataModel.setRowCount(lazyDataModel.getRowCount() - 1);
    }

    /**
     * Initialize the process of creation by
     * {@link #setSelectedValue(Serializable) setting} new value.
     *
     * @see #getNewValue()
     * @see #setSelectedValue(Serializable)
     */
    public void preCreate() {
        setSelectedValue(getNewValue());
    }

    /**
     * Update the selected value.<br>
     * Call when the user click on <i>Submit</i> button into <i>Update view</i>.
     * <p>
     * {@link #refresh() Refresh} list after update.
     *
     * @see #getSelectedValue()
     * @see AbstractService#update(Serializable)
     */
    public void update() {
        try {
            getService().update(getSelectedValue());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Element updated with success", null));
            refresh();
        } catch (RuntimeException e) {
            LOGGER.warn("Error during updating", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error during updating", null));
        }
    }

}