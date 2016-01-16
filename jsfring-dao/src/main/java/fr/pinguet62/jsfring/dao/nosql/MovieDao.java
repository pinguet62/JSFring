package fr.pinguet62.jsfring.dao.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import fr.pinguet62.jsfring.model.nosql.Movie;

/** @see Movie */
@Repository
public interface MovieDao extends MongoRepository<Movie, String> {}