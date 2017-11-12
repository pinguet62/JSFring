package fr.pinguet62.jsfring.dao.sql.common;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Common methods for {@link Repository}s.
 * <p>
 * Extension of {@link JpaRepository} and {@link QuerydslPredicateExecutor}, with custom shared methods.
 */
@NoRepositoryBean
public interface CommonRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T> {

    // Iterable to List
    @Override
    List<T> findAll(OrderSpecifier<?>... orders);

    // Iterable to List
    @Override
    List<T> findAll(Predicate predicate);

}