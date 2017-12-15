package fr.pinguet62.jsfring.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.common.PasswordGenerator;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.Serializable;
import java.util.Random;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.util.ReactorMatchers.hasCount;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

/**
 * @see AbstractService
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootConfig.class)
// DbUnit
@TestExecutionListeners(mergeMode = MERGE_WITH_DEFAULTS, listeners = DbUnitTestExecutionListener.class)
@DatabaseSetup(DATASET)
public class AbstractServiceTest {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private RightService rightService;

    @Autowired
    private UserService userService;

    /**
     * @see AbstractService#create(Serializable)
     */
    @Test
    public void test_create() {
        {
            long initialCount = profileService.getAll().count().block();
            profileService.create(Profile.builder().title("new profile").build());
            assertThat(profileService.getAll(), hasCount(initialCount + 1));
        }
        {
            long initialCount = userService.getAll().count().block();
            userService.create(User.builder().email("foo@bar.org").password(new PasswordGenerator().get()).active(new Random().nextBoolean()).build());
            assertThat(userService.getAll(), hasCount(initialCount + 1));
        }
    }

    /**
     * @see AbstractService#delete(Serializable)
     */
    @Test
    public void test_delete() {
        profileService.delete(profileService.get(1).block());
        assertThat(profileService.getAll(), hasCount(1));

        profileService.delete(profileService.get(2).block());
        assertThat(profileService.getAll(), hasCount(0));
    }

    /**
     * @see AbstractService#get(Serializable)
     */
    @Test
    public void test_get() {
        {
            assertThat(rightService.get("RIGHT_RO").block().getTitle(), is(equalTo("Affichage des droits")));
            assertThat(rightService.get("PROFILE_RO").block().getTitle(), is(equalTo("Affichage des profils")));
        }
        {
            assertThat(profileService.get(1).block().getTitle(), is(equalTo("Profile admin")));
            assertThat(profileService.get(2).block().getTitle(), is(equalTo("User admin")));
        }
        {
            assertThat(userService.get("root@admin.fr").block().getPassword(), is(equalTo("{noop}Azerty1!")));
        }
    }

    /**
     * @see AbstractService#getAll()
     */
    @Test
    public void test_getAll() {
        assertThat(rightService.getAll(), hasCount(5));
        assertThat(profileService.getAll(), hasCount(2));
        assertThat(userService.getAll(), hasCount(3));
    }

    /**
     * @see AbstractService#update(Serializable)
     */
    @Test
    public void test_update() {
        int id = 1;
        String newTitle = "new title";

        // Before
        Profile profile = profileService.get(id).block();
        assertThat(profile.getTitle(), is(not(equalTo(newTitle))));

        profile.setTitle(newTitle);
        profileService.update(profile);

        // Test
        assertThat(profileService.get(id).block().getTitle(), is(equalTo(newTitle)));
    }

}