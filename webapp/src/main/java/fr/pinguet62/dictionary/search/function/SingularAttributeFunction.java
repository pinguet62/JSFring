package fr.pinguet62.dictionary.search.function;

import javax.persistence.metamodel.SingularAttribute;

/**
 * @param <F>
 *            The base type.
 * @param <S>
 *            The target type.
 */
public final class SingularAttributeFunction<F, S> extends Function<S> {

    private final SingularAttribute<F, S> attribute;

    public SingularAttributeFunction(SingularAttribute<F, S> attribute) {
        this.attribute = attribute;
    }

    public SingularAttribute<F, S> getAttribute() {
        return attribute;
    }

}
