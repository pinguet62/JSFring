package fr.pinguet62.dictionary.search.condition;

import javax.persistence.metamodel.SingularAttribute;

/**
 * @param <F>
 *            The base type.
 * @param <T>
 *            The type to compare.
 */
public final class EqualsCondition<F, T> extends Condition {

    private final SingularAttribute<F, T> attribute;

    private final T value;

    public EqualsCondition(SingularAttribute<F, T> attribute, T value) {
        this.attribute = attribute;
        this.value = value;
    }

    public SingularAttribute<F, T> getAttribute() {
        return attribute;
    }

    public T getValue() {
        return value;
    }

}
