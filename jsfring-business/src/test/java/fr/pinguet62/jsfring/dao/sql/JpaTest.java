package fr.pinguet62.jsfring.dao.sql;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.querydsl.core.types.Predicate;
import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.QProfile;
import fr.pinguet62.jsfring.model.sql.QRight;
import fr.pinguet62.jsfring.model.sql.Right;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.sql.DataSource;
import java.util.List;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Simple tests for JPA relationships.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootConfig.class)
@Transactional
// DbUnit
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, TransactionalTestExecutionListener.class})
@DatabaseSetup(DATASET)
public class JpaTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private RightDao rightDao;

    /**
     * When another existing element is {@link List#add(Object) added} to relationship, the {@link List} of entities must be
     * updated.
     *
     * @see ManyToMany
     */
    @Test
    public void test_add_existing() {
        System.out.println(ds);
        final int id = profileDao.findAll().get(0).getId();

        // Initial state
        Profile profile = profileDao.getOne(id);
        int initialCount = profile.getRights().size();

        // Find existing & Not associated
        List<String> associatedCodes = profile.getRights().stream().map(Right::getCode).collect(toList());
        Predicate notAssociatedFilter = QRight.right_.code.notIn(associatedCodes);
        Right notAssociated = rightDao.findAll(notAssociatedFilter).get(0);

        // Associate
        profile.getRights().add(notAssociated);
        assertThat(profile.getRights(), hasSize(initialCount + 1));
        profileDao.save(profile);

        // Check
        profileDao.flush();
        assertThat(profileDao.getOne(id).getRights(), hasSize(initialCount + 1));
    }

    /**
     * When new element is {@link List#add(Object) added} to relationship, the update must fail.
     *
     * @see ManyToMany
     * @see EntityNotFoundException
     */
    @Test
    public void test_add_new() {
        final int id = profileDao.findAll().get(0).getId();

        // Initial state
        Profile profile = profileDao.getOne(id);
        int initialCount = profile.getRights().size();

        // Create new & Not associated
        String code = randomUUID().toString().substring(0, 4);
        assertThat(rightDao.existsById(code), is(false));
        Right newRight = Right.builder().code(code).build();

        // Associate
        profile.getRights().add(newRight);
        assertThat(profile.getRights(), hasSize(initialCount + 1));
        try {
            profileDao.save(profile);
        } catch (Exception e) {
            assertTrue(
                    // Native
                    e instanceof EntityNotFoundException
                            // Spring Wrapper
                            || e.getCause() instanceof EntityNotFoundException);
        }
    }

    /**
     * The access to relationship must fetch the {@link List} of associated entities.
     *
     * @see ManyToMany#fetch()
     * @see FetchType#LAZY
     */
    @Test
    public void test_lazyLoad() {
        Profile profile = profileDao.getOne(1);
        assertThat(profile.getRights(), hasSize(3));
    }

    /**
     * When associated element is {@link List#remove(Object) removed} from relationship, the relation is updated.
     *
     * @see ManyToMany
     */
    @Test
    public void test_remove() {
        final int id = profileDao.findAll(QProfile.profile.rights.isNotEmpty()).get(0).getId();

        // Initial state
        Profile profile = profileDao.getOne(id);
        int initialCount = profile.getRights().size();

        // Remove an associated entity
        profile.getRights().remove(profile.getRights().iterator().next());
        assertThat(profile.getRights(), hasSize(initialCount - 1));
        profileDao.save(profile);

        // Check
        profileDao.flush();
        assertThat(profileDao.getOne(id).getRights(), hasSize(initialCount - 1));
    }

}