package fr.pinguet62.jsfring.dao.jpa;

import static fr.pinguet62.jsfring.Config.DATASET;
import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;
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

import fr.pinguet62.jsfring.config.Application;
import fr.pinguet62.jsfring.dao.ProfileDao;
import fr.pinguet62.jsfring.dao.RightDao;
import fr.pinguet62.jsfring.model.Profile;

/** @see ManyToMany */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@DatabaseSetup(DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@Transactional
public class ManyToManyTest {

    @Inject
    private ProfileDao profileDao;

    @Inject
    private RightDao rightDao;

    /**
     * When new element is {@link List#add(Object) added} to relationship, the
     * {@link List} of objects must be updated.
     */
    @Test
    public void test_add() {
        final int id = 1;

        // Before
        Profile profile = profileDao.get(id);
        long initialCount = profile.getRights().size();

        profile.getRights().add(rightDao.get("USER_RO"));
        assertEquals(initialCount + 1, profile.getRights().size());
        profileDao.update(profile);

        // Test
        long newCount = profileDao.get(id).getRights().size();
        assertEquals(initialCount + 1, newCount);
    }

    /**
     * The access to relationship must fetch the {@link List} of associated
     * objects.
     *
     * @see ManyToMany#fetch()
     * @see FetchType#LAZY
     */
    @Test
    public void test_lazyLoad() {
        Profile profile = profileDao.get(1);

        assertEquals(3, profile.getRights().size());
    }

}