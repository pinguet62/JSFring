package fr.pinguet62.jsfring.dao.nosql;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import fr.pinguet62.jsfring.model.nosql.Comment;

/** @see Comment */
@Repository
public interface CommentDao extends MongoRepository<Comment, ObjectId> {}