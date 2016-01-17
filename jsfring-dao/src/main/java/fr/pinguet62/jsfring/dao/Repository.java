package fr.pinguet62.jsfring.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;

public interface Repository<T, ID extends Serializable> extends JpaRepository<T, ID>, QueryDslPredicateExecutor<T> {

    SearchResults<T> findPanginated(JPAQuery query);

}