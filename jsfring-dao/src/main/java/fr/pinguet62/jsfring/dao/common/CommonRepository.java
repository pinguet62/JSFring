package fr.pinguet62.jsfring.dao.common;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;

/**
 * Common methods for {@link Repository}s.
 * <p>
 * Extension of {@link JpaRepository} and {@link QueryDslPredicateExecutor},
 * with custom shared methods.
 */
@NoRepositoryBean // Required: "No managed bean Object"
public interface CommonRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, QueryDslPredicateExecutor<T> {

    /**
     * Find all objects who match to the {@link JPAQuery}:
     * <ul>
     * <li>{@link Predicate} for filtering</li>
     * <li>{@link OrderSpecifier} for sorting</li>
     * </ul>
     *
     * @param query The {@link JPAQuery}.
     * @return The objects found.
     */
    List<T> find(JPAQuery query);

    @Override
    List<T> findAll(Predicate predicate);

    /**
     * Find all objects who match to the {@link JPAQuery}:
     * <ul>
     * <li>{@link Predicate} for filtering</li>
     * <li>{@link OrderSpecifier} for sorting</li>
     * <li>Pagination</li>
     * </ul>
     *
     * @param query The {@link JPAQuery}.
     * @return The paginated objects found.
     */
    SearchResults<T> findPaginated(JPAQuery query);

}