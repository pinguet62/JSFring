package fr.pinguet62.jsfring.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.GeneratedValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.Config;
import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.model.QRight;
import fr.pinguet62.jsfring.model.Right;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.service.UserService.PasswordGenerator;

/** @see AbstractDao */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
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
            profileDao.create(new Profile("new profile"));
            assertEquals(count + 1, profileDao.count());
        }
        {
            long count = userDao.count();
            userDao.create(new User("new login", new PasswordGenerator().get(),
                    "foo@hostname.domain"));
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
        profileDao.create(new Profile("new profile"));
        assertEquals(initialCount + 1, profileDao.count());
    }

    /** @see AbstractDao#delete(Object) */
    @Test
    public void test_delete() {
        assertEquals(2, profileDao.count());
        profileDao.delete(profileDao.get(1));
        assertEquals(1, profileDao.count());
        profileDao.delete(profileDao.get(2));
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
        QRight r = QRight.right;
        JPAQuery query = new JPAQuery().from(r).where(
                r.code.contains("PROFILE"));

        List<Right> rights = rightDao.find(query);

        assertEquals(2, rights.size());
        rights.stream().allMatch(
                right -> Arrays.asList("PROFILE_RO", "PROFILE_RW").contains(
                        right.getTitle()));
    }

    /** @see AbstractDao#find(JPAQuery) */
    @Test
    public void test_find_notFound() {
        QRight r = QRight.right;
        JPAQuery query = new JPAQuery().from(r).where(r.code.contains("#$!@"));

        List<Right> rights = rightDao.find(query);

        assertTrue(rights.isEmpty());
    }

    /**
     * @see AbstractDao#findPanginated(JPAQuery)
     * @see SearchResults#getResults()
     * @see SearchResults#getTotal()
     */
    @Test
    public void test_findPanginated() {
        int pageSize = 2;

        final long totalCount = rightDao.count();
        JPAQuery query = new JPAQuery().from(QRight.right);
        {
            SearchResults<Right> page1 = rightDao.findPanginated(query.clone()
                    .limit(2).offset(0 * pageSize));
            assertEquals(2, page1.getResults().size());
            assertEquals(totalCount, page1.getTotal());
        }
        {
            SearchResults<Right> page2 = rightDao.findPanginated(query.clone()
                    .limit(2).offset(1 * pageSize));
            assertEquals(2, page2.getResults().size());
            assertEquals(totalCount, page2.getTotal());
        }
        {
            SearchResults<Right> page3 = rightDao.findPanginated(query.clone()
                    .limit(2).offset(2 * pageSize));
            assertEquals(1, page3.getResults().size());
            assertEquals(totalCount, page3.getTotal());
        }
        for (int n = 3; n <= 5; n++) {
            SearchResults<Right> pageN = rightDao.findPanginated(query.clone()
                    .limit(2).offset(n * pageSize));
            assertTrue(pageN.getResults().isEmpty());
            assertEquals(totalCount, pageN.getTotal());
        }
    }

    /**
     * @see AbstractDao#findPanginated(JPAQuery)
     * @see SearchResults#getResults()
     * @see SearchResults#getTotal()
     */
    @Test
    public void test_findPanginated_notFound() {
        QRight r = QRight.right;
        JPAQuery query = new JPAQuery().from(r).where(r.code.contains("#$!@"));

        SearchResults<Right> page = rightDao.findPanginated(query.limit(2)
                .offset(0));

        assertTrue(page.getResults().isEmpty());
        assertEquals(0, page.getTotal());
    }

    /** @see AbstractDao#get(java.io.Serializable) */
    @Test
    public void test_get() {
        {
            assertEquals("Affichage des profils", rightDao.get("RIGHT_RO")
                    .getTitle());
            assertEquals("Affichage des profils", rightDao.get("PROFILE_RO")
                    .getTitle());
        }
        {
            assertEquals("Profile admin", profileDao.get(1).getTitle());
            assertEquals("User admin", profileDao.get(2).getTitle());
        }
        {
            assertEquals("password", userDao.get("super admin").getPassword());
            assertEquals("admin_profile@domain.fr", userDao
                    .get("admin profile").getEmail());
        }
    }

    /** @see AbstractDao#get(java.io.Serializable) */
    @Test
    public void test_get_notExisting() {
        assertNull(profileDao.get(-1));
        assertNull(profileDao.get(99));
    }

    /** @see AbstractDao#getAll() */
    @Test
    public void test_getAll() {
        assertEquals(5, rightDao.getAll().size());
        assertEquals(2, profileDao.getAll().size());
        assertEquals(3, userDao.getAll().size());
    }

    /** @see AbstractDao#update(Object) */
    @Test
    public void test_update() {
        Profile profile = profileDao.get(1);

        profile.setTitle("new title");
        profileDao.update(profile);

        assertEquals("new title", profileDao.get(1).getTitle());
    }

    /** @see AbstractDao#update(Object) */
    @Test
    public void test_update_new() {
        int id = 1;
        String newTitle = "new title";

        Profile profile = profileDao.get(id);
        assertNotEquals(newTitle, profile.getTitle());

        profile.setTitle(newTitle);
        profileDao.update(profile);
        assertEquals("new title", profileDao.get(id).getTitle());
    }

}