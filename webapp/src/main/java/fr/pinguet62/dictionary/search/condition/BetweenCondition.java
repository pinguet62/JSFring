package fr.pinguet62.dictionary.search.condition;

import javax.persistence.metamodel.SingularAttribute;

/**
 * @param <F>
 *            The base type.
 * @param <T>
 *            The type to compare.
 */
public final class BetweenCondition<F, T extends Comparable<? super T>> extends
        Condition {

    private final SingularAttribute<F, ? extends T> attribute;

    private final T inf;

    private final T sup;

    public BetweenCondition(T inf, SingularAttribute<F, ? extends T> attribute,
            T sup) {
        this.inf = inf;
        this.attribute = attribute;
        this.sup = sup;
    }

    public SingularAttribute<F, ? extends T> getAttribute() {
        return attribute;
    }

    public T getInf() {
        return inf;
    }

    public T getSup() {
        return sup;
    }

}
