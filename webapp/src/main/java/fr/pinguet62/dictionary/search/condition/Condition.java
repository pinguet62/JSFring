package fr.pinguet62.dictionary.search.condition;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

public abstract class Condition {

    public static Condition And(Condition where1, Condition where2,
            Condition... whereN) {
        List<Condition> list = new LinkedList<>();
        list.add(where1);
        list.add(where2);
        list.addAll(Arrays.asList(whereN));
        return new AndCondition(list.toArray(new Condition[list.size()]));
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
