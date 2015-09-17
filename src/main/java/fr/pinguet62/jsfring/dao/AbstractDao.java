package fr.pinguet62.jsfring.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Expression;

/**
 * The generic DAO for entities.
 *
 * @param <T> The {@link Entity} type.
 * @param <PK> The Primary key type.
 */
@Named
public abstract class AbstractDao<T, PK extends Serializable> {

    /** The {@link EntityManager}. */
    @PersistenceContext
    protected EntityManager em;

    /**
     * The {@link Class} of the current {@link Entity}.<br>
     * Used by {@code Criteria} to determinate the target table.
     */
    @SuppressWarnings("unchecked")
    private final Class<T> type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    /**
     * Get number of objects.
     *
     * @return The number of objects.
     */
    public long count() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(type)));
        return em.createQuery(cq).getSingleResult();
    }

    /**
     * Create new object.
     *
     * @param object The object.
     * @return The created object.
     */
    public T create(T object) {
        em.persist(object);
        return object;
    }

    /**
     * Delete the object.
     *
     * @param object The object to delete.
     */
    public void delete(T object) {
        Object id = em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(object);
        T obj = em.getReference(type, id);
        em.remove(obj);
    }

    /** Delete all objects of table. */
    public void deleteAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<T> cd = cb.createCriteriaDelete(type);
        cd.from(type);
        em.createQuery(cd).executeUpdate();
    }

    /**
     * Find all objects who match to the {@link JPAQuery}.
     *
     * @param query The {@link JPAQuery}.
     * @return The objects found.
     */
    public List<T> find(JPAQuery query) {
        JPAQuery emQuery = query.clone(em);
        return emQuery.list(getBaseExpression());
    }

    public SearchResults<T> findPanginated(JPAQuery query) {
        JPAQuery emQuery = query.clone(em);
        return emQuery.listResults(getBaseExpression());
    }

    /**
     * Get the object by id.<br>
     * Detach object of database.
     *
     * @param id The id.
     * @return The object, {@code null} if not found.
     */
    public T get(PK id) {
        return em.find(type, id);
    }

    /**
     * Get all objects.
     * <p>
     * <b>WARNING:</b> If the number of objects is too large, prefer the
     * {@link #find(JPAQuery) find} method with {@link JPAQuery#limit(long)
     * limit}.
     *
     * @return The list of objects.
     */
    public List<T> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(type);
        Root<T> rootEntry = cq.from(type);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    /**
     * Get the default {@link Expression}.
     *
     * @return The {@link Expression}.
     * @see JPAQuery
     */
    protected abstract Expression<T> getBaseExpression();

    /**
     * Update an object.
     *
     * @param object The object to update.
     * @return The updated object.
     */
    public T update(T object) {
        return em.merge(object);
    }

}