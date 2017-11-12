package fr.pinguet62.jsfring.dao.nosql;

import fr.pinguet62.jsfring.model.nosql.Person;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @see Person
 */
@Repository
public interface PersonDao extends MongoRepository<Person, ObjectId>, QuerydslPredicateExecutor<Person> {
}