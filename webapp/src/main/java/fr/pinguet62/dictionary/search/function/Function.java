package fr.pinguet62.dictionary.search.function;

/**
 * @param <S>
 *            The target type.
 */
public class Function<S> {

    public static Function<Long> Count() {
        return new CountFunction();
    }

    public static <F> Function<F> Identity() {
        return new IdentityFunction<F>();
    }

    // public static <X> Function<X> Max(Result<Q, List<X>> ensemble) {
    // return new MaxFunction<X>(ensemble);
    // }

}