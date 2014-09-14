package fr.pinguet62.dictionary.search;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import fr.pinguet62.dictionary.search.condition.Condition;
import fr.pinguet62.dictionary.search.function.Function;
import fr.pinguet62.dictionary.search.function.IdentityFunction;
import fr.pinguet62.dictionary.search.function.SingularAttributeFunction;

/**
 * The core of search & filter.
 *
 * @param <F>
 *            The base type.
 */
public final class Query<F> {

    /**
     * Factory method to build a new {@link Query}.
     *
     * @param <X>
     *            The base type.
     * @param root
     *            The base type.
     * @return New {@link Query}.
     */
    public static <F> Query<F> From(Class<F> root) {
        return new Query<F>(root);
    }

    /** The {@code HAVING} clauses. */
    private final List<Condition> havings = new LinkedList<>();

    /** The {@code ORDER BY} clauses. */
    private final List<Order<F>> orderbys = new LinkedList<>();

    /** The {@code FROM} clause. */
    private final Class<F> root;

    /** The {@code WHERE} clauses. */
    private final List<Condition> wheres = new LinkedList<>();

    /**
     * Private constructor.
     *
     * @param root
     *            The base type.
     * @see #From(Class)
     */
    private Query(Class<F> root) {
        this.root = root;
    }

    public List<Condition> getHavings() {
        return havings;
    }

    public List<Order<F>> getOrderbys() {
        return orderbys;
    }

    public Class<F> getRootType() {
        return root;
    }

    public List<Condition> getWheres() {
        return wheres;
    }

    /**
     * Add new {@link Condition}.
     *
     * @param condition
     *            The {@link Condition}.
     * @return This instance.
     */
    public Query<F> having(Condition condition) {
        havings.add(condition);
        return this;
    }

    /**
     * Add new {@link Order}.<br/>
     * By default the order is ascending.
     *
     * @param attribute
     *            The attribute that sort result.
     * @return This instance.
     */
    public Query<F> orderBy(SingularAttribute<F, ?> attribute) {
        return orderBy(attribute, true);
    }

    /**
     * Add new {@link Order}.
     *
     * @param attribute
     *            The attribute that sort result.
     * @param ascending
     *            {@code true} for ascending sort, {@code false} for descending.
     * @return This instance.
     */
    public Query<F> orderBy(SingularAttribute<F, ?> attribute, boolean ascending) {
        orderbys.add(new Order<F>(attribute, ascending));
        return this;
    }

    /**
     * Get the root type. Is equivalent to the SQL request {@code SELECT *}.
     * <p>
     * This is a terminal operation.
     *
     * @return The {@link Result}.
     */
    public Result<F, F> select() {
        return new Result<F, F>(this, new IdentityFunction<F>());
    }

    // TODO
    public <S> Result<F, S> select(Function<S> function) {
        return new Result<F, S>(this, function);
    }

    /**
     * Map the root type to another type.
     * <p>
     * This is a terminal operation.
     *
     * @param attribute
     *            The {@link SingularAttribute} to map.
     * @return The {@link Result}.
     */
    public <S> Result<F, S> select(SingularAttribute<F, S> attribute) {
        return new Result<F, S>(this, new SingularAttributeFunction<F, S>(
                attribute));
    }

    /**
     * Add new {@link Condition}.
     *
     * @param condition
     *            The {@link Condition}.
     * @return This instance.
     */
    public Query<F> where(Condition condition) {
        wheres.add(condition);
        return this;
    }

}
