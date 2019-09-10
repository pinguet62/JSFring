package fr.pinguet62.jsfring.dao.nosql;

import fr.pinguet62.jsfring.model.nosql.Movie;
import fr.pinguet62.jsfring.model.nosql.Person;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @see MongoRepository
 */
@DataMongoTest
@ExtendWith(SpringExtension.class)
public class NoSQLRepositoryTest {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private UserDao userDao;

    /**
     * @see MongoRepository#insert(Object)
     */
    @Test
    public void test_idAlreadyExists() {
        Person existing = personDao.findAll().get(0);

        Person entityWithSameId = new Person();
        entityWithSameId.setId(existing.getId());
        entityWithSameId.setName(randomUUID().toString());

        assertThrows(DuplicateKeyException.class, () -> personDao.insert(entityWithSameId));
    }

    /**
     * @see MongoRepository#insert(Object)
     */
    @Test
    public void test_new() {
        long initialCount = personDao.count();

        Person entity = new Person();
        entity.setName(randomUUID().toString());
        personDao.insert(entity);

        long actualCount = personDao.count();
        assertThat(actualCount, is(equalTo(initialCount + 1)));
    }

    /**
     * @see MongoRepository#findAll()
     */
    @Test
    @Disabled // TODO
    public void test_findAll() {
        assertThat(userDao.findAll(), is(not(empty())));
        assertThat(personDao.findAll(), is(not(empty())));
        assertThat(movieDao.findAll(), is(not(empty())));
    }

    /**
     * @see DBRef
     */
    @Test
    @Disabled // TODO
    public void test_oneToMany() {
        ObjectId id = movieDao.findAll().get(0).getId();

        Optional<Movie> movie = movieDao.findById(id);
        assertThat(movie.get().getComments().get(0).getUser().getPseudo(), is(notNullValue()));
    }

}