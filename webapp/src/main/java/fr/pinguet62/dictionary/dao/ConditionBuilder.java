package fr.pinguet62.dictionary.dao;

import java.util.function.Function;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import fr.pinguet62.dictionary.search.condition.BetweenCondition;
import fr.pinguet62.dictionary.search.condition.Condition;
import fr.pinguet62.dictionary.search.condition.EqualsCondition;

/**
 * Convert {@link Condition} to JPA {@link Predicate}.
 *
 * @param <F>
 *            The base type.
 */
public final class ConditionBuilder<F> implements
        Function<Condition, Predicate> {

    private final CriteriaBuilder cb;

    private final Root<F> from;

    public ConditionBuilder(CriteriaBuilder cb, Root<F> from) {
        this.cb = cb;
        this.from = from;
    }

    @Override
    public Predicate apply(Condition condition) {
        if (condition instanceof BetweenCondition)
            return applyBetweenCondition(cb, from, condition);
        else if (condition instanceof EqualsCondition) {
            EqualsCondition equalsCondition = (EqualsCondition) condition;
            return cb.equal(from.get(equalsCondition.getAttribute()),
                    equalsCondition.getValue());
        } else
            throw new UnsupportedOperationException("Unknow condition: "
                    + condition);
    }

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

        return cb.between(from.get(attribute), betweenCondition.getInf(),
                betweenCondition.getSup());
    }

}
