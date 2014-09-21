package fr.pinguet62.dictionary.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.dictionary.dao.AbstractDao;

/**
 * The generic service for entities.
 *
 * @param <T>
 *            The {@link Entity} type.
 * @param <PK>
 *            The Primary key type.
 */
public abstract class AbstractService<T, PK extends Serializable> {

    /** The {@link AbstractDao}. */
    protected final AbstractDao<T, PK> dao;

    /**
     * Constructor with {@link AbstractDao}.<br/>
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

    // TODO Comments
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
     * @return The objects.
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
    public void update(T object) {
        dao.update(object);
    }

}
