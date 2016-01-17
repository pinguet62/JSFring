package fr.pinguet62.jsfring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import fr.pinguet62.jsfring.model.Profile;

/** @see Profile */
@Repository
public interface ProfileDao extends JpaRepository<Profile, Integer>, QueryDslPredicateExecutor<Profile> {}