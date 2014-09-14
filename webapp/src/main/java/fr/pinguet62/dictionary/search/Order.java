package fr.pinguet62.dictionary.search;

import javax.persistence.metamodel.SingularAttribute;

/**
 * @param <F>
 *            The base type.
 */
public final class Order<F> {

    private final boolean ascending;

    private final SingularAttribute<F, ?> attribute;

    public Order(SingularAttribute<F, ?> attribute, boolean ascending) {
        this.attribute = attribute;
        this.ascending = ascending;
    }

    public SingularAttribute<F, ?> getAttribute() {
        return attribute;
    }

    public boolean isAscending() {
        return ascending;
    }

}