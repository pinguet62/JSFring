package fr.pinguet62.dictionary.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

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

import fr.pinguet62.dictionary.dao.AbstractDao;
import fr.pinguet62.dictionary.dao.RightSearchDao;
import fr.pinguet62.dictionary.model.Profile;
import fr.pinguet62.dictionary.model.Profile_;
import fr.pinguet62.dictionary.model.Right;
import fr.pinguet62.dictionary.model.Right_;
import fr.pinguet62.dictionary.search.condition.Condition;

/**
 * @see AbstractDao
 * @see Query
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
@DatabaseSetup("/dataset.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@Transactional
public class CriteriaQueryBuilderTest {

    @Autowired
    RightSearchDao rightDao;

    @Test
    public void count() {
        Query<Right> query = Query.From(Right.class);
        long count = rightDao.count(query);
        assertEquals(5, count);
    }

    @Test
    public void find_all() {
        Result<Right, Right> query = Query.From(Right.class).select();
        List<Right> rights = rightDao.find(query);
        assertEquals(5, rights.size());
    }

    @Test
    public void find_attribute() {
        Result<Right, String> query = Query.From(Right.class).select(
                Right_.code);

        List<String> codes = rightDao.find(query);
        assertEquals(5, codes.size());
    }

    @Test
    public void find_where() {
        Result<Right, Right> query = Query.From(Right.class)
                .where(Condition.Equals(Right_.code, "RIGHT_RO")).select();
        List<Right> rights = rightDao.find(query);
        assertEquals(1, rights.size());
    }

    @Test
    public void where_between() {
        Result<Profile, Profile> query = Query.From(Profile.class)
                .where(Condition.Between(1, Profile_.id, 1)).select();
        Profile profile = rightDao.get(query);
        assertNotNull(profile);
        assertEquals("Profile admin", profile.getTitle());
    }

}
