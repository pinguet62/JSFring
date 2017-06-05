package fr.pinguet62.jsfring.dao.nosql;

import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.test.context.junit4.SpringRunner;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.model.nosql.Movie;
import fr.pinguet62.jsfring.model.nosql.Person;

/**
 * @see QueryDslMongoRepository
 * @see MongoRepository
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class)
public class NoSQLRepositoryTest {

    @Inject
    private MovieDao movieDao;

    @Inject
    private PersonDao personDao;

    @Inject
    private UserDao userDao;

    /** @see MongoRepository#insert(Object) */
    @Test(expected = DuplicateKeyException.class)
    public void test_create_idAlreadyExists() {
        Person existing = personDao.findAll().get(0);

        Person entityWithSameId = new Person();
        entityWithSameId.setId(existing.getId());
        entityWithSameId.setName(randomUUID().toString());
        personDao.insert(entityWithSameId);
    }

    /** @see MongoRepository#insert(Object) */
    @Test
    public void test_create_new() {
        long initialCount = personDao.count();

        Person entity = new Person();
        entity.setName(randomUUID().toString());
        personDao.insert(entity);

        long actualCount = personDao.count();
        assertThat(actualCount, is(equalTo(initialCount + 1)));
    }

    /** @see MongoRepository#findAll() */
    // @Test
    public void test_findAll() {
        assertThat(userDao.findAll(), is(not(empty())));
        assertThat(personDao.findAll(), is(not(empty())));
        assertThat(movieDao.findAll(), is(not(empty())));
    }

    /** @see DBRef */
    // @Test
    public void test_oneToMany() {
        ObjectId id = movieDao.findAll().get(0).getId();

        Optional<Movie> movie = movieDao.findById(id);
        assertThat(movie.get().getComments().get(0).getUser().getPseudo(), is(notNullValue()));
    }

}