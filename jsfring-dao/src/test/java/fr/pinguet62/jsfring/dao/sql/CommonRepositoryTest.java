package fr.pinguet62.jsfring.dao.sql;

import static fr.pinguet62.jsfring.test.Config.DATASET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.persistence.GeneratedValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Predicate;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.dao.sql.common.CommonRepository;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.QRight;
import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.util.PasswordGenerator;

/** @see CommonRepository */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@Transactional
public class CommonRepositoryTest {

    @Inject
    private ProfileDao profileDao;

    @Inject
    private RightDao rightDao;

    @Inject
    private UserDao userDao;

    /** @see CrudRepository#count() */
    @Test
    public void test_count() {
        assertEquals(5, rightDao.count());
        assertEquals(2, profileDao.count());
        assertEquals(3, userDao.count());
    }

    /** @see CrudRepository#delete(Object) */
    @Test
    public void test_delete() {
        assertEquals(2, profileDao.count());
        profileDao.delete(profileDao.getOne(1));
        assertEquals(1, profileDao.count());
        profileDao.delete(profileDao.getOne(2));
        assertEquals(0, profileDao.count());
    }

    /** @see CrudRepository#deleteAll() */
    @Test
    public void test_deleteAll() {
        assertEquals(2, profileDao.count());
        // not empty
        profileDao.deleteAll();
        assertEquals(0, profileDao.count());
        // already empty
        profileDao.deleteAll();
    }

    /** @see CommonRepository#find(JPAQuery) */
    @Test
    public void test_find_JPAQuery() {
        final String keyword = "PROFILE";

        QRight r = QRight.right_;
        JPAQuery query = new JPAQuery().from(r).where(r.code.contains(keyword));
        List<Right> rights = rightDao.find(query);

        assertTrue(rights.stream().allMatch(right -> right.getCode().contains(keyword)));
    }

    /** @see JpaRepository#findAll() */
    @Test
    public void test_findAll() {
        assertEquals(5, rightDao.findAll().size());
        assertEquals(2, profileDao.findAll().size());
        assertEquals(3, userDao.findAll().size());
    }

    /** @see QueryDslPredicateExecutor#findAll(Predicate) */
    @Test
    public void test_findAll_Predicate() {
        final String keyword = "PROFILE";

        Predicate predicate = QRight.right_.code.contains(keyword);
        List<Right> rights = rightDao.findAll(predicate);

        assertTrue(rights.stream().allMatch(right -> right.getCode().contains(keyword)));
    }

    /** @see QueryDslPredicateExecutor#findAll(Predicate) */
    @Test
    public void test_findAll_Predicate_notFound() {
        List<Right> rights = rightDao.findAll(QRight.right_.code.contains("#$!@"));
        assertTrue(rights.isEmpty());
    }

    /**
     * Get an entity from its key.
     *
     * @see JpaRepository#getOne(Serializable)
     */
    @Test
    public void test_getOne() {
        {
            assertEquals("Affichage des droits", rightDao.getOne("RIGHT_RO").getTitle());
            assertEquals("Affichage des profils", rightDao.getOne("PROFILE_RO").getTitle());
        }
        {
            assertEquals("Profile admin", profileDao.getOne(1).getTitle());
            assertEquals("User admin", profileDao.getOne(2).getTitle());
        }
        {
            assertEquals("Azerty1!", userDao.getOne("super admin").getPassword());
            assertEquals("admin_profile@domain.fr", userDao.getOne("admin profile").getEmail());
        }
    }

    /**
     * If the entity doesn't exists, an {@link EntityNotFoundException} must be
     * thrown.
     *
     * @see JpaRepository#getOne(Serializable)
     */
    @Test
    public void test_getOne_notExisting() {
        final int id = -1;
        assertFalse(profileDao.exists(id));

        try {
            assertNull(profileDao.getOne(id));
            fail();
        } catch (EntityNotFoundException e) {}
    }

    /** @see CrudRepository#save(Object) */
    @Test
    public void test_save_create() {
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
     * @see CrudRepository#save(Object)
     * @see GeneratedValue
     */
    @Test
    public void test_save_create_autoIncrement() {
        profileDao.save(new Profile("new profile"));
    }

    /**
     * Update a <i>simple</i> (not an <i>association</i>) field.
     *
     * @see JpaRepository#save(Object)
     */
    @Test
    public void test_save_update() {
        final int id = profileDao.findAll().get(0).getId();
        final String newTitle = "new title";

        // Initial state
        Profile profile = profileDao.getOne(id);
        assertNotEquals(newTitle, profile.getTitle());

        // Update
        profile.setTitle(newTitle);
        profileDao.save(profile);

        // Check
        profileDao.flush();
        assertEquals(newTitle, profileDao.getOne(1).getTitle());
    }

}