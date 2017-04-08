package fr.pinguet62.jsfring.dao.nosql;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import fr.pinguet62.jsfring.model.nosql.Person;

/** @see Person */
@Repository
public interface PersonDao extends MongoRepository<Person, ObjectId>, QueryDslPredicateExecutor<Person> {
}