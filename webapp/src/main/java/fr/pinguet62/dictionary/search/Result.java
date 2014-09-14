package fr.pinguet62.dictionary.search;

import fr.pinguet62.dictionary.search.function.Function;

/**
 * @param <F>
 *            The base type of {@link Query}.
 * @param <S>
 *            The target type.
 */
public class Result<F, S> {

    private final Function<S> function;

    final Query<F> query;

    public Result(Query<F> query, Function<S> function) {
        this.query = query;
        this.function = function;
    }

    public Function<S> getFunction() {
        return function;
    }

    public Query<F> getQuery() {
        return query;
    }

    public Class<S> getTargetType() {
        return null;
    }

}