package fr.pinguet62.dictionary.dao;

import static org.junit.Assert.assertEquals;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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

import fr.pinguet62.dictionary.model.Profile;

/** Tests of database tables associations. */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
@DatabaseSetup("/dataset.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@Transactional
public class RelationshipTest {

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private RightDao rightDao;

    /** Test the association {@link ManyToMany}. */
    @Test
    public void test_manyToMany_add() {
        Profile profile1 = profileDao.get(1);
        long initialCount = profile1.getRights().size();
        profile1.getRights().add(rightDao.get("USER_RO"));
        assertEquals(initialCount + 1, profile1.getRights().size());
        profileDao.update(profile1);

        Profile profile2 = profileDao.get(1);
        assertEquals(initialCount + 1, profile2.getRights().size());
    }

    /** Test the association {@link ManyToMany}. */
    @Test
    public void test_manyToMany_load() {
        Profile profile = profileDao.get(1);
        assertEquals(3, profile.getRights().size());
    }

    /** Test the association {@link ManyToOne}. */
    // @Test
    public void test_manyToOne() {
        // TODO
    }

    /** Test the association {@link OneToMany}. */
    // @Test
    public void test_oneToMany() {
        // TODO
    }

    /** Test the association {@link OneToOne}. */
    // @Test
    public void test_oneToOne() {
        // TODO
    }

}
