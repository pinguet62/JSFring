package fr.pinguet62.jsfring.service;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.Serializable;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.util.PasswordGenerator;

/** @see AbstractService */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class AbstractServiceTest {

    @Inject
    private ProfileService profileService;

    @Inject
    private RightService rightService;

    @Inject
    private UserService userService;

    /** @see AbstractService#create(Serializable) */
    @Test
    public void test_create() {
        {
            long count = profileService.getAll().size();
            profileService.create(new Profile("new profile"));
            assertEquals(count + 1, profileService.getAll().size());
        }
        {
            long count = userService.getAll().size();
            userService.create(new User("new login", new PasswordGenerator().get(), "foo@hostname.domain"));
            assertEquals(count + 1, userService.getAll().size());
        }
    }

    /** @see AbstractService#delete(Serializable) */
    @Test
    public void test_delete() {
        profileService.delete(profileService.get(1));
        assertEquals(1, profileService.getAll().size());
        profileService.delete(profileService.get(2));
        assertEquals(0, profileService.getAll().size());
    }

    /** @see AbstractService#get(Serializable) */
    @Test
    public void test_get() {
        {
            assertEquals("Affichage des droits", rightService.get("RIGHT_RO").getTitle());
            assertEquals("Affichage des profils", rightService.get("PROFILE_RO").getTitle());
        }
        {
            assertEquals("Profile admin", profileService.get(1).getTitle());
            assertEquals("User admin", profileService.get(2).getTitle());
        }
        {
            assertEquals("Azerty1!", userService.get("super admin").getPassword());
            assertEquals("admin_profile@domain.fr", userService.get("admin profile").getEmail());
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