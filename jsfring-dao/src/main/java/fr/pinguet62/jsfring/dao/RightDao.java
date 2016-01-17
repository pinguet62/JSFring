package fr.pinguet62.jsfring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import fr.pinguet62.jsfring.model.Right;

/** @see Right */
@Repository
public interface RightDao extends JpaRepository<Right, String>, QueryDslPredicateExecutor<Right> {}