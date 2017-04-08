package fr.pinguet62.jsfring.dao.sql.common;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * Common methods for {@link Repository}s.
 * <p>
 * Extension of {@link JpaRepository} and {@link QueryDslPredicateExecutor}, with custom shared methods.
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
    List<T> find(JPAQuery<T> query);

    @Override // Iterable to List
    List<T> findAll(OrderSpecifier<?>... orders);

    @Override // Iterable to List
    List<T> findAll(Predicate predicate);

}