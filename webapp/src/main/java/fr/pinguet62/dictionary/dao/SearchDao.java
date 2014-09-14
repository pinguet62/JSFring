package fr.pinguet62.dictionary.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.stereotype.Repository;

import fr.pinguet62.dictionary.search.Order;
import fr.pinguet62.dictionary.search.Query;
import fr.pinguet62.dictionary.search.Result;
import fr.pinguet62.dictionary.search.condition.BetweenCondition;
import fr.pinguet62.dictionary.search.condition.Condition;
import fr.pinguet62.dictionary.search.condition.EqualsCondition;
import fr.pinguet62.dictionary.search.function.CountFunction;
import fr.pinguet62.dictionary.search.function.Function;
import fr.pinguet62.dictionary.search.function.IdentityFunction;
import fr.pinguet62.dictionary.search.function.SingularAttributeFunction;

@Repository
public abstract class SearchDao<T, PK extends Serializable> extends
AbstractDao<T, PK> {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    /**
     * Needed to cast the {@link Condition} to target class who implements
     * {@link Comparable}.
     *
     * @param <Y>
     *            Type who implements {@link Comparable}.
     * @param condition
     *            The {@link BetweenCondition}.
     * @return The {@link Predicate}.
     */
    private <F, Y extends Comparable<? super Y>> Predicate applyBetweenCondition(
            CriteriaBuilder cb, Root<F> from, Condition condition) {
        BetweenCondition<F, Y> betweenCondition = (BetweenCondition<F, Y>) condition;
        SingularAttribute<F, ? extends Y> attribute = betweenCondition
                .getAttribute();

        Predicate p = cb.between(from.get(attribute),
                betweenCondition.getInf(), betweenCondition.getSup());
        return p;
    }

    public <F> long count(Query<F> query) {
        return get(query.select(Function.Count()));
    }

    // F => T
    public <F, S> List<S> find(Result<F, S> queryResult) {
        TypedQuery<S> typedQuery = getTypedQuery(queryResult);
        List<S> result = typedQuery.getResultList();
        return result;
    }

    // F => T
    public <F, S> S get(Result<F, S> queryResult) {
        TypedQuery<S> typedQuery = getTypedQuery(queryResult);
        S result = typedQuery.getSingleResult();
        return result;
    }

    // F => T
    private <F, S> TypedQuery<S> getTypedQuery(Result<F, S> queryResult) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Result type
        CriteriaQuery<S> resultQuery = cb.createQuery(queryResult
                .getTargetType());

        Query<F> query = queryResult.getQuery();

        // FROM
        Root<F> from = resultQuery.from(query.getRootType());
        // ORDER
        for (Order<F> order : query.getOrderbys())
            if (order.isAscending())
                resultQuery.orderBy(cb.asc(from.get(order.getAttribute())));
            else
                resultQuery.orderBy(cb.asc(from.get(order.getAttribute())));
        // TODO HAVING

        // TODO WHERE
        for (Condition condition : query.getWheres()) {
            Predicate p;
            if (condition instanceof BetweenCondition)
                p = applyBetweenCondition(cb, from, condition);
            else if (condition instanceof EqualsCondition) {
                EqualsCondition equalsCondition = (EqualsCondition) condition;
                p = cb.equal(from.get(equalsCondition.getAttribute()),
                        equalsCondition.getValue());
            } else
                throw new UnsupportedOperationException("Unknow condition: "
                        + condition);
            resultQuery.where(p);
        }

        // SELECT
        Function<S> function = queryResult.getFunction();
        if (function instanceof IdentityFunction)
            resultQuery.select((Selection<? extends S>) from);
        else if (function instanceof CountFunction)
            resultQuery.select((Selection<? extends S>) cb.count(from));
        else if (function instanceof SingularAttributeFunction) {
            SingularAttributeFunction<F, S> singularAttributeFunction = (SingularAttributeFunction<F, S>) function;
            SingularAttribute<?, S> singularAttribute = singularAttributeFunction
                    .getAttribute();
            resultQuery.select(from
                    .get((SingularAttribute<? super F, S>) singularAttribute));
        }
        // TODO else

        return em.createQuery(resultQuery);
    }

}
