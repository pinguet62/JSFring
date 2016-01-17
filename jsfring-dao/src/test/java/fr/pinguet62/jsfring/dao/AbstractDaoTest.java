package fr.pinguet62.jsfring.dao;

import static fr.pinguet62.jsfring.test.Config.DATASET;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.persistence.GeneratedValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.google.common.collect.Iterables;
import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.model.QRight;
import fr.pinguet62.jsfring.model.Right;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.util.PasswordGenerator;

/** @see AbstractDao */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@Transactional
public class AbstractDaoTest {

    @Inject
    private ProfileDao profileDao;

    @Inject
    private RightDao rightDao;

    @Inject
    private UserDao userDao;

    /** @see AbstractDao#count() */
    @Test
    public void test_count() {
        assertEquals(5, rightDao.count());
        assertEquals(2, profileDao.count());
        assertEquals(3, userDao.count());
    }

    /** @see AbstractDao#create(Object) */
    @Test
    public void test_create() {
        {
            long count = profileDao.count();
            profileDao.save(new Profile("new profile"));
            assertEquals(count + 1, profileDao.count());
        }
        {
            long count = userDao.count();
            userDao.save(new User("new login", new PasswordGenerator().get(), "foo@hostname.domain"));
            assertEquals(count + 1, userDao.count());
        }
    }

    /**
     * The auto-incremented ID must be automatically set during creation.
     *
     * @see AbstractDao#create(Object)
     * @see GeneratedValue
     */
    @Test
    public void test_create_autoIncrement() {
        long initialCount = profileDao.count();
        assertEquals(initialCount, profileDao.count());
        profileDao.save(new Profile("new profile"));
        assertEquals(initialCount + 1, profileDao.count());
    }

    /** @see AbstractDao#delete(Object) */
    @Test
    public void test_delete() {
        assertEquals(2, profileDao.count());
        profileDao.delete(profileDao.findOne(1));
        assertEquals(1, profileDao.count());
        profileDao.delete(profileDao.findOne(2));
        assertEquals(0, profileDao.count());
    }

    /** @see AbstractDao#deleteAll() */
    @Test
    public void test_deleteAll() {
        assertEquals(2, profileDao.count());
        // not empty
        profileDao.deleteAll();
        assertEquals(0, profileDao.count());
        // already empty
        profileDao.deleteAll();
    }

    /** @see AbstractDao#find(JPAQuery) */
    @Test
    public void test_find() {
        Iterable<Right> rights = rightDao.findAll(QRight.right_.code.contains("PROFILE"));

        assertEquals(2, Iterables.size(rights));
        StreamSupport.stream(rights.spliterator(), false)
                .allMatch(r -> asList("PROFILE_RO", "PROFILE_RW").contains(r.getTitle()));
    }

    /** @see AbstractDao#find(JPAQuery) */
    @Test
    public void test_find_notFound() {
        Iterable<Right> rights = rightDao.findAll(QRight.right_.code.contains("#$!@"));
        assertTrue(Iterables.isEmpty(rights));
    }

    /** @see AbstractDao#get(java.io.Serializable) */
    @Test
    public void test_get() {
        {
            assertEquals("Affichage des droits", rightDao.findOne("RIGHT_RO").getTitle());
            assertEquals("Affichage des profils", rightDao.findOne("PROFILE_RO").getTitle());
        }
        {
            assertEquals("Profile admin", profileDao.findOne(1).getTitle());
            assertEquals("User admin", profileDao.findOne(2).getTitle());
        }
        {
            assertEquals("Azerty1!", userDao.findOne("super admin").getPassword());
            assertEquals("admin_profile@domain.fr", userDao.findOne("admin profile").getEmail());
        }
    }

    /** @see AbstractDao#get(java.io.Serializable) */
    @Test
    public void test_get_notExisting() {
        assertNull(profileDao.findOne(-1));
        assertNull(profileDao.findOne(99));
    }

    /** @see AbstractDao#getAll() */
    @Test
    public void test_getAll() {
        assertEquals(5, rightDao.findAll().size());
        assertEquals(2, profileDao.findAll().size());
        assertEquals(3, userDao.findAll().size());
    }

    /** @see AbstractDao#update(Object) */
    @Test
    public void test_update() {
        Profile profile = profileDao.findOne(1);

        profile.setTitle("new title");
        profileDao.save(profile);

        assertEquals("new title", profileDao.findOne(1).getTitle());
    }

    /** @see AbstractDao#update(Object) */
    @Test
    public void test_update_new() {
        int id = 1;
        String newTitle = "new title";

        Profile profile = profileDao.findOne(id);
        assertNotEquals(newTitle, profile.getTitle());

        profile.setTitle(newTitle);
        profileDao.save(profile);
        assertEquals("new title", profileDao.findOne(id).getTitle());
    }

}