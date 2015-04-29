package fr.pinguet62.jsfring.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.jsfring.dao.AbstractDao;

/**
 * The generic service for entities.
 *
 * @param <T>
 *            The type of objects.
 * @param <PK>
 *            The Primary key type.
 */
public abstract class AbstractService<T, PK extends Serializable> {

    /** The {@link AbstractDao}. */
    protected final AbstractDao<T, PK> dao;

    /**
     * Constructor with {@link AbstractDao}.<br>
     * The classes who inherit of this class must call this constructor with the
     * associated {@link AbstractDao}.
     *
     * @param dao
     *            The {@link AbstractDao}.
     */
    protected AbstractService(AbstractDao<T, PK> dao) {
        this.dao = dao;
    }

    /**
     * Get number of objects.
     *
     * @return The number of objects.
     */
    @Transactional
    public long count() {
        return dao.count();
    }

    /**
     * Create new object.
     *
     * @param object
     *            The object.
     * @return The created object.
     */
    @Transactional
    public T create(T object) {
        return dao.create(object);
    }

    /**
     * Delete the object.
     *
     * @param object
     *            The object to delete.
     */
    @Transactional
    public void delete(T object) {
        dao.delete(object);
    }

    /**
     * Get list of object from {@link JPAQuery}.
     *
     * @return The objects found.
     */
    @Transactional(readOnly = true)
    public List<T> find(JPAQuery query) {
        return dao.find(query);
    }

    /**
     * Get list of objects from paginated {@link JPAQuery}.
     *
     * @return The {@link SearchResults} who contains paginated objects.
     */
    @Transactional(readOnly = true)
    public SearchResults<T> findPanginated(JPAQuery query) {
        return dao.findPanginated(query);
    }

    /**
     * Get the object by id.
     *
     * @param id
     *            The id.
     * @return The object, {@code null} if not found.
     */
    @Transactional(readOnly = true)
    public T get(PK id) {
        return dao.get(id);
    }

    /**
     * Get all objects.
     *
     * @return All objects.
     */
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return dao.getAll();
    }

    /**
     * Update an object.
     *
     * @param object
     *            The object to update.
     * @return The updated object.
     */
    @Transactional
    public T update(T object) {
        return dao.update(object);
    }

}