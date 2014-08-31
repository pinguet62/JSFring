package fr.pinguet62.dictionary.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import fr.pinguet62.dictionary.filter.PaginationResult;
import fr.pinguet62.dictionary.filter.SearchFilter;

/**
 * The generic DAO for entities.
 *
 * @param <T>
 *            The {@link Entity} type.
 * @param <PK>
 *            The Primary key type.
 */
@Repository
public abstract class AbstractDao<T, PK extends Serializable> {

    /** Apply the filter on fields to the {@link CriteriaQuery}. */
    private static <T> void applyFilter(CriteriaBuilder cb,
            CriteriaQuery<?> resultQuery, Root<T> from,
            Map<String, Object> filters) {
        if (filters == null)
            return;

        for (Entry<String, Object> filter : filters.entrySet())
            resultQuery.where(cb.like(from.get(filter.getKey()),
                    "%" + filter.getValue() + "%"));
    }

    /** The {@link EntityManager}. */
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    /**
     * The {@link Class} of the current {@link Entity}.<br/>
     * Used by {@code Criteria} to determinate the target table.
     */
    @SuppressWarnings("unchecked")
    private final Class<T> type = (Class<T>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];

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
     * @param object
     *            The object.
     * @return The created object.
     */
    public T create(T object) {
        em.persist(object);
        return object;
    }

    /**
     * Delete the object.
     *
     * @param object
     *            The object to delete.
     */
    public void delete(T object) {
        Object id = em.getEntityManagerFactory().getPersistenceUnitUtil()
                .getIdentifier(object);
        T obj = em.getReference(type, id);
        em.remove(obj);
    }

    /**
     * Get the {@link Criteria} of object.
     *
     * @return The {@link Criteria}.
     */
    // public Criteria getCriteria() {
    // Session session = HibernateUtils.getSession();
    // return session.createCriteria(type);
    // }

    /** Delete all objects of table. */
    public void deleteAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<T> cd = cb.createCriteriaDelete(type);
        cd.from(type);
        em.createQuery(cd).executeUpdate();
    }

    /**
     * Find objects by filter.
     *
     * @param filter
     *            The {@link SearchFilter}.
     * @return The list of found objects.
     */
    public PaginationResult<T> find(SearchFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Total count
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<T> from1 = countQuery.from(type);
        applyFilter(cb, countQuery, from1, filter.getFieldFilters());
        countQuery.select(cb.count(from1));
        long totalCount = em.createQuery(countQuery).getSingleResult();

        // Results
        CriteriaQuery<T> resultQuery = cb.createQuery(type);
        Root<T> from2 = resultQuery.from(type);
        applyFilter(cb, resultQuery, from2, filter.getFieldFilters());
        resultQuery.select(from2);
        TypedQuery<T> typedQuery = em.createQuery(resultQuery);
        typedQuery.setFirstResult(filter.getFirstIndex());
        typedQuery.setMaxResults(filter.getNumberPerPage());
        List<T> results = typedQuery.getResultList();

        return new PaginationResult<T>(results, totalCount);
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
        return em.find(type, id);
    }

    /**
     * Get all objectSs<br/>
     * WARNING: If the number of objects is too large!
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
     * Update an object.
     *
     * @param object
     *            The object to update.
     * @return The updated object.
     */
    public T update(T object) {
        return em.merge(object);
    }

}
