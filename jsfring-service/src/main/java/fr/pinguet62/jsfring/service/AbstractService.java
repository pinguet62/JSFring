package fr.pinguet62.jsfring.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.springframework.cache.Cache;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Predicate;

import fr.pinguet62.jsfring.dao.Repository;

/**
 * The generic service for entities.
 * <p>
 * <u>Cache:</u> All methods use the {@link Cache}.
 *
 * @param <T> The type of objects.
 * @param <PK> The <i>Primary key</i> type.<br>
 *            To be managed by {@link Cache}, {@link Object#hashCode()} and
 *            {@link Object#equals(Object)} methods must be {@link Override
 *            overridden}.
 */
public abstract class AbstractService<T extends Serializable, ID extends Serializable> {

    /** The {@link AbstractDao}. */
    @Inject
    protected Repository<T, ID> dao;

    /**
     * Get number of objects.
     *
     * @return The number of objects.
     */
    @Transactional(readOnly = true)
    public long count() {
        return dao.count();
    }

    /**
     * Create new object.
     *
     * @param object The object.
     * @return The created object.
     */
    @Transactional
    public T create(T object) {
        return dao.save(object);
    }

    /**
     * Delete the object.
     *
     * @param object The object to delete.
     */
    @Transactional
    public void delete(T object) {
        dao.delete(object);
    }

    /**
     * Find list of objects.
     *
     * @param predicate The {@link Predicate}.
     * @return The objects found.
     */
    @Transactional(readOnly = true)
    public Iterable<T> findAll(Predicate predicate) {
        return dao.findAll(predicate);
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
     * @param id The id.
     * @return The object, {@code null} if not found.
     */
    @Transactional(readOnly = true)
    public T get(ID id) {
        return dao.findOne(id);
    }

    /**
     * Get all objects.
     *
     * @return All objects.
     */
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return dao.findAll();
    }

    /**
     * Update an object.
     *
     * @param object The object to update.
     * @return The updated object.
     */
    @Transactional
    public T update(T object) {
        return dao.save(object);
    }

}