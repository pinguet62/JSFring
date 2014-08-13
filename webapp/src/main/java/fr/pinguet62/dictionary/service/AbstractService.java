package fr.pinguet62.dictionary.service;

import java.io.Serializable;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;

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

    /** The DAO. */
    @Autowired
    protected AbstractDao<T, PK> dao;

    /**
     * Create new object.
     *
     * @param object
     *            The object.
     * @return The created object.
     */
    public T create(T object) {
        return dao.create(object);
    }

    /**
     * Get the object by id. <br/>
     * Detach object of database.
     *
     * @param id
     *            The id.
     * @return The object, {@code null} if not found.
     */
    public T get(PK id) {
        return dao.get(id);
    }

}
