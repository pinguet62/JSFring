package fr.pinguet62.jsfring.dao.sql;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.querydsl.core.types.Predicate;
import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.common.PasswordGenerator;
import fr.pinguet62.jsfring.dao.sql.common.CommonRepository;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.QRight;
import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.model.sql.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.GeneratedValue;
import java.util.List;
import java.util.Random;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static fr.pinguet62.jsfring.util.MatcherUtils.mappedTo;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

/**
 * @see CommonRepository
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootConfig.class)
@Transactional
// DbUnit
@TestExecutionListeners(mergeMode = MERGE_WITH_DEFAULTS, listeners = DbUnitTestExecutionListener.class)
@DatabaseSetup(DATASET)
public class CommonRepositoryTest {

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private RightDao rightDao;

    @Autowired
    private UserDao userDao;

    /**
     * @see CrudRepository#count()
     */
    @Test
    public void test_count() {
        assertThat(rightDao.count(), is(equalTo(5L)));
        assertThat(profileDao.count(), is(equalTo(2L)));
        assertThat(userDao.count(), is(equalTo(3L)));
    }

    /**
     * @see CrudRepository#delete(Object)
     */
    @Test
    public void test_delete() {
        assertThat(profileDao.count(), is(equalTo(2L)));
        profileDao.delete(profileDao.getOne(1));
        assertThat(profileDao.count(), is(equalTo(1L)));
        profileDao.delete(profileDao.getOne(2));
        assertThat(profileDao.count(), is(equalTo(0L)));
    }

    /**
     * @see CrudRepository#deleteAll()
     */
    @Test
    public void test_deleteAll() {
        assertThat(profileDao.count(), is(equalTo(2L)));
        // not empty
        profileDao.deleteAll();
        assertThat(profileDao.count(), is(equalTo(0L)));
        // already empty
        profileDao.deleteAll();
    }

    /**
     * @see JpaRepository#findAll()
     */
    @Test
    public void test_findAll() {
        assertThat(rightDao.findAll(), hasSize(5));
        assertThat(profileDao.findAll(), hasSize(2));
        assertThat(userDao.findAll(), hasSize(3));
    }

    /**
     * @see QuerydslPredicateExecutor#findAll(Predicate)
     */
    @Test
    public void test_findAll_Predicate() {
        final String keyword = "PROFILE";

        Predicate predicate = QRight.right_.code.contains(keyword);
        List<Right> rights = rightDao.findAll(predicate);

        assertThat(rights, everyItem(mappedTo(Right::getCode, containsString(keyword))));
    }

    /**
     * @see QuerydslPredicateExecutor#findAll(Predicate)
     */
    @Test
    public void test_findAll_Predicate_notFound() {
        List<Right> rights = rightDao.findAll(QRight.right_.code.contains("#$!@"));
        assertThat(rights, is(empty()));
    }

    /**
     * Get an entity from its key.
     *
     * @see JpaRepository#getOne(Object)
     */
    @Test
    public void test_getOne() {
        {
            assertThat(rightDao.getOne("RIGHT_RO").getTitle(), is(equalTo("Affichage des droits")));
            assertThat(rightDao.getOne("PROFILE_RO").getTitle(), is(equalTo("Affichage des profils")));
        }
        {
            assertThat(profileDao.getOne(1).getTitle(), is(equalTo("Profile admin")));
            assertThat(profileDao.getOne(2).getTitle(), is(equalTo("User admin")));
        }
        {
            assertThat(userDao.getOne("root@admin.fr").getPassword(), is(equalTo("{noop}Azerty1!")));
        }
    }

    /**
     * If the entity doesn't exists, an {@link EntityNotFoundException} must be thrown.
     *
     * @see JpaRepository#getOne(Object)
     */
    @Test
    public void test_getOne_notExisting() {
        final int id = -1;
        assertThat(profileDao.existsById(id), is(false));

        try {
            profileDao.getOne(id).getTitle(); // fail
            fail();
        } catch (EntityNotFoundException e) {
        }
    }

    /**
     * @see CrudRepository#save(Object)
     */
    @Test
    public void test_save_create() {
        {
            long count = profileDao.count();
            profileDao.save(Profile.builder().title("new profile").build());
            assertThat(profileDao.count(), is(equalTo(count + 1)));
        }
        {
            long count = userDao.count();
            userDao.save(
                    User.builder()
                            .email("foo@bar.fr")
                            .password(new PasswordGenerator().get())
                            .active(new Random().nextBoolean())
                            .build());
            assertThat(userDao.count(), is(equalTo(count + 1)));
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
        profileDao.save(Profile.builder().title("new profile").build());
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
        assertThat(profile.getTitle(), is(not(equalTo(newTitle))));

        // Update
        profile.setTitle(newTitle);
        profileDao.save(profile);

        // Check
        profileDao.flush();
        assertThat(profileDao.getOne(1).getTitle(), is(equalTo(newTitle)));
    }

}