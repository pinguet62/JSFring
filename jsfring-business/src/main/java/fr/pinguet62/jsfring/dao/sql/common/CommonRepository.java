package fr.pinguet62.jsfring.dao.sql.common;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

/**
 * Common methods for {@link Repository}s.
 * <p>
 * Extension of {@link JpaRepository} and {@link QueryDslPredicateExecutor}, with custom shared methods.
 */
@NoRepositoryBean
public interface CommonRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T> {

    @Override // Iterable to List
    List<T> findAll(OrderSpecifier<?>... orders);

    @Override // Iterable to List
    List<T> findAll(Predicate predicate);

}