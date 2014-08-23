package fr.pinguet62.dictionary.dao.relationship;

import static org.junit.Assert.assertEquals;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.dictionary.dao.ProfileDao;
import fr.pinguet62.dictionary.dao.RightDao;
import fr.pinguet62.dictionary.model.Profile;

/**
 * Tests for "many to many" associations of database tables.
 *
 * @see ManyToMany
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
@DatabaseSetup("/dataset.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
@Transactional
public class ManyToManyTest {

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private RightDao rightDao;

    /** @see ManyToMany */
    @Test
    public void test_add() {
        Profile profile = profileDao.get(1);
        long initialCount = profile.getRights().size();
        // add
        profile.getRights().add(rightDao.get("USER_RO"));
        assertEquals(initialCount + 1, profile.getRights().size());
        profileDao.update(profile);
        // test
        assertEquals(initialCount + 1, profileDao.get(1).getRights().size());
    }

    /**
     * @see ManyToMany#fetch()
     * @see FetchType#LAZY
     */
    @Test
    public void test_lazyLoad() {
        Profile profile = profileDao.get(1);
        assertEquals(3, profile.getRights().size());
    }

}
