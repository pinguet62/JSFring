package fr.pinguet62.dictionary.search.condition;

import javax.persistence.metamodel.SingularAttribute;

class BaseCondition extends Condition {
}

class CompositeCondition extends Condition {
}

public abstract class Condition {

    public static Condition And(Condition where1, Condition where2,
            Condition... whereN) {
        return null;
    }

    public static <F, T extends Comparable<? super T>> Condition Between(T inf,
            SingularAttribute<F, ? extends T> attribute, T sup) {
        return new BetweenCondition<F, T>(inf, attribute, sup);
    }

    public static <F, T> Condition Equals(SingularAttribute<F, T> attribute,
            T value) {
        return new EqualsCondition<F, T>(attribute, value);
    }

    public static Condition Or(Condition where1, Condition where2,
            Condition... whereN) {
        return null;
    }

}
