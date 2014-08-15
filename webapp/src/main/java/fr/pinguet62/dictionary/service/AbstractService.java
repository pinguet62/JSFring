package fr.pinguet62.dictionary.service;

import java.io.Serializable;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.pinguet62.dictionary.dao.AbstractDao;

/**
 * The generic service for entities.
 *
 * @param <T>
 *            The {@link Entity} type.
 * @param <PK>
 *            The Primary key type.
 */
@Service
public abstract class AbstractService<T, PK extends Serializable> {

    /** The {@link AbstractDao}. */
    @Autowired
    protected AbstractDao<T, PK> dao;

    /**
     * Get number of objects.
     *
     * @return The number of objects.
     */
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

}
