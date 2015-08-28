package fr.pinguet62.jsfring.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;

import fr.pinguet62.Config;
import fr.pinguet62.jsfring.dao.AbstractDao;
import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.model.QRight;
import fr.pinguet62.jsfring.model.Right;
import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.service.UserService.PasswordGenerator;

/** @see AbstractService */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class AbstractServiceTest {

    @Inject
    private ProfileService profileService;

    @Inject
    private RightService rightService;

    @Inject
    private UserService userService;

    /** @see AbstractService#count() */
    @Test
    public void test_count() {
        assertEquals(5, rightService.count());
        assertEquals(2, profileService.count());
        assertEquals(3, userService.count());
    }

    /** @see AbstractService#create(Serializable) */
    @Test
    public void test_create() {
        {
            long count = profileService.count();
            profileService.create(new Profile("new profile"));
            assertEquals(count + 1, profileService.count());
        }
        {
            long count = userService.count();
            userService.create(new User("new login", new PasswordGenerator()
                    .get(), "foo@hostname.domain"));
            assertEquals(count + 1, userService.count());
        }
    }

    /** @see AbstractService#delete(Serializable) */
    @Test
    public void test_delete() {
        profileService.delete(profileService.get(1));
        assertEquals(1, profileService.count());
        profileService.delete(profileService.get(2));
        assertEquals(0, profileService.count());
    }

    /** @see AbstractService#find(JPAQuery) */
    @Test
    public void test_find() {
        QRight r = QRight.right;
        JPAQuery query = new JPAQuery().from(r).where(
                r.code.contains("PROFILE"));

        List<Right> rights = rightService.find(query);

        assertEquals(2, rights.size());
        rights.stream().allMatch(
                right -> Arrays.asList("PROFILE_RO", "PROFILE_RW").contains(
                        right.getTitle()));
    }

    /**
     * @see AbstractService#findPanginated(JPAQuery)
     * @see SearchResults#getResults()
     * @see SearchResults#getTotal()
     */
    @Test
    public void test_findPanginated() {
        JPAQuery query = new JPAQuery().from(QRight.right);

        SearchResults<Right> page2 = rightService.findPanginated(query.clone()
                .limit(2).offset(2));

        assertEquals(2, page2.getResults().size());
        assertEquals(rightService.count(), page2.getTotal());
    }

    /** @see AbstractService#get(Serializable) */
    @Test
    public void test_get() {
        {
            assertEquals("Affichage des profils", rightService.get("RIGHT_RO")
                    .getTitle());
            assertEquals("Affichage des profils", rightService
                    .get("PROFILE_RO").getTitle());
        }
        {
            assertEquals("Profile admin", profileService.get(1).getTitle());
            assertEquals("User admin", profileService.get(2).getTitle());
        }
        {
            assertEquals("password", userService.get("super admin")
                    .getPassword());
            assertEquals("admin_profile@domain.fr",
                    userService.get("admin profile").getEmail());
        }
    }

    /** @see AbstractDao#getAll() */
    @Test
    public void test_getAll() {
        assertEquals(5, rightService.getAll().size());
        assertEquals(2, profileService.getAll().size());
        assertEquals(3, userService.getAll().size());
    }

    /** @see AbstractService#update(Object) */
    @Test
    public void test_update() {
        int id = 1;
        String newTitle = "new title";

        // Before
        Profile profile = profileService.get(id);
        assertNotEquals(newTitle, profile.getTitle());

        profile.setTitle(newTitle);
        profileService.update(profile);

        // Test
        assertEquals("new title", profileService.get(id).getTitle());
    }

}