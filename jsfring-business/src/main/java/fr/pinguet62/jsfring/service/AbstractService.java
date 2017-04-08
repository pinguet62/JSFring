package fr.pinguet62.jsfring.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.springframework.cache.Cache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import fr.pinguet62.jsfring.dao.sql.common.CommonRepository;

/**
 * The generic service for entities.
 * <p>
 * <u>Cache:</u> To enable caching, {@link Override} method and use {@link Cache} methods.
 *
 * @param <T>
 *            The type of objects.
 * @param <ID>
 *            The Primary key type.<br>
 *            To be managed by {@link Cache}, {@link Object#hashCode()} and {@link Object#equals(Object)} methods must be
 *            {@link Override overridden}.
 */
public abstract class AbstractService<T extends Serializable, ID extends Serializable> {

    /** The {@link CommonRepository}. */
    @Inject
    protected CommonRepository<T, ID> dao;

    /**
     * Create new object.
     *
     * @param object
     *            The object.
     * @return The created object.
     * @see CommonRepository#save(Object)
     */
    @Transactional
    public T create(T object) {
        return dao.save(object);
    }

    /**
     * Delete the object.
     *
     * @param object
     *            The object to delete.
     * @see CommonRepository#delete(Object)
     */
    @Transactional
    public void delete(T object) {
        dao.delete(object);
    }

    /**
     * Find list of objects.
     *
     * @param predicate
     *            The {@link Predicate}.
     * @return The objects found.
     * @see CommonRepository#findAll(Predicate)
     */
    @Transactional(readOnly = true)
    public List<T> findAll(Predicate predicate) {
        return dao.findAll(predicate);
    }

    /**
     * Find paginated results.
     *
     * @param predicate
     *            The {@link Predicate} used to filter results.
     * @param pageable
     *            The {@link Pageable} used to define current pagination, and sort results.
     * @return The {@link Page paginated results}.
     * @see CommonRepository#findAll(Predicate, Pageable)
     */
    @Transactional(readOnly = true)
    public Page<T> findAll(Predicate predicate, Pageable pageable) {
        return dao.findAll(predicate, pageable);
    }

    /**
     * Get the object by id.
     *
     * @param id
     *            The id.
     * @return The object, {@code null} if not found.
     * @see CrudRepository#findOne(Serializable)
     */
    @Transactional(readOnly = true)
    public T get(ID id) {
        return dao.findOne(id);
    }

    /**
     * Get all objects.
     *
     * @return All objects.
     * @see CommonRepository#findAll()
     */
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return dao.findAll();
    }

    /**
     * Update an object.
     *
     * @param object
     *            The object to update.
     * @return The updated object.
     * @see CommonRepository#save(Object)
     */
    @Transactional
    public T update(T object) {
        return dao.save(object);
    }

}