package fr.pinguet62.jsfring.dao.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import fr.pinguet62.jsfring.model.nosql.Author;

/** @see Author */
@Repository
public interface AuthorDao extends MongoRepository<Author, String> {}