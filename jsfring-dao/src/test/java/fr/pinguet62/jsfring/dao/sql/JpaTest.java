package fr.pinguet62.jsfring.dao.sql;

import static fr.pinguet62.jsfring.test.DbUnitConfig.DATASET;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

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
import com.mysema.query.types.Predicate;

import fr.pinguet62.jsfring.SpringBootConfig;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.QProfile;
import fr.pinguet62.jsfring.model.sql.QRight;
import fr.pinguet62.jsfring.model.sql.Right;

/** Simple tests for JPA relationships. */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringBootConfig.class)
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@Transactional
public class JpaTest {

    @Inject
    private ProfileDao profileDao;

    @Inject
    private RightDao rightDao;

    /**
     * When another existing element is {@link List#add(Object) added} to
     * relationship, the {@link List} of entities must be updated.
     *
     * @see ManyToMany
     */
    @Test
    public void test_add_existing() {
        final int id = profileDao.findAll().get(0).getId();

        // Initial state
        Profile profile = profileDao.getOne(id);
        long initialCount = profile.getRights().size();

        // Find existing & Not associated
        List<String> associatedCodes = profile.getRights().stream().map(Right::getCode).collect(toList());
        Predicate notAssociatedFilter = QRight.right_.code.notIn(associatedCodes);
        Right notAssociated = rightDao.findAll(notAssociatedFilter).get(0);

        // Associate
        profile.getRights().add(notAssociated);
        assertEquals(initialCount + 1, profile.getRights().size());
        profileDao.save(profile);

        // Check
        profileDao.flush();
        long newCount = profileDao.getOne(id).getRights().size();
        assertEquals(initialCount + 1, newCount);
    }

    /**
     * When new element is {@link List#add(Object) added} to relationship, the
     * update must fail.
     *
     * @see ManyToMany
     * @see EntityNotFoundException
     */
    @Test
    public void test_add_new() {
        final int id = profileDao.findAll().get(0).getId();

        // Initial state
        Profile profile = profileDao.getOne(id);
        long initialCount = profile.getRights().size();

        // Create new & Not associated
        String code = UUID.randomUUID().toString().substring(0, 4);
        assertFalse(rightDao.exists(code));
        Right newRight = new Right(code);

        // Associate
        profile.getRights().add(newRight);
        assertEquals(initialCount + 1, profile.getRights().size());
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
     * The access to relationship must fetch the {@link List} of associated
     * entities.
     *
     * @see ManyToMany#fetch()
     * @see FetchType#LAZY
     */
    @Test
    public void test_lazyLoad() {
        Profile profile = profileDao.getOne(1);

        assertEquals(3, profile.getRights().size());
    }

    /**
     * When associated element is {@link List#remove(Object) removed} from
     * relationship, the relation is updated.
     *
     * @see ManyToMany
     */
    @Test
    public void test_remove() {
        final int id = profileDao.findAll(QProfile.profile.rights.isNotEmpty()).get(0).getId();

        // Initial state
        Profile profile = profileDao.getOne(id);
        long initialCount = profile.getRights().size();

        // Remove an associated entity
        profile.getRights().remove(profile.getRights().iterator().next());
        assertEquals(initialCount - 1, profile.getRights().size());
        profileDao.save(profile);

        // Check
        profileDao.flush();
        long newCount = profileDao.getOne(id).getRights().size();
        assertEquals(initialCount - 1, newCount);
    }

}