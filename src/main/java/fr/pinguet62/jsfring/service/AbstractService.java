package fr.pinguet62.jsfring.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.jsfring.dao.AbstractDao;

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
public abstract class AbstractService<T extends Serializable, PK extends Serializable> {

    /** The {@link AbstractDao}. */
    protected final AbstractDao<T, PK> dao;

    /** Key of the {@link Cache}. */
    private static final String CACHE = "cache";

    /**
     * Constructor with {@link AbstractDao}.<br>
     * The classes who inherit of this class must call this constructor with the
     * associated {@link AbstractDao}.
     *
     * @param dao The {@link AbstractDao}.
     */
    protected AbstractService(AbstractDao<T, PK> dao) {
        this.dao = dao;
    }

    /**
     * Get number of objects.
     *
     * @return The number of objects.
     */
    @Cacheable(CACHE)
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
    @CacheEvict(CACHE)
    @Transactional
    public T create(T object) {
        return dao.create(object);
    }

    /**
     * Delete the object.
     *
     * @param object The object to delete.
     */
    @CacheEvict(CACHE)
    @Transactional
    public void delete(T object) {
        dao.delete(object);
    }

    /**
     * Get list of object from {@link JPAQuery}.
     *
     * @return The objects found.
     */
    @Cacheable(CACHE)
    @Transactional(readOnly = true)
    public List<T> find(JPAQuery query) {
        return dao.find(query);
    }

    /**
     * Get list of objects from paginated {@link JPAQuery}.
     *
     * @return The {@link SearchResults} who contains paginated objects.
     */
    @Cacheable(CACHE)
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
    @Cacheable(CACHE)
    @Transactional(readOnly = true)
    public T get(PK id) {
        return dao.get(id);
    }

    /**
     * Get all objects.
     *
     * @return All objects.
     */
    @Cacheable(CACHE)
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return dao.getAll();
    }

    /**
     * Update an object.
     *
     * @param object The object to update.
     * @return The updated object.
     */
    @CacheEvict(CACHE)
    @Transactional
    public T update(T object) {
        return dao.update(object);
    }

}