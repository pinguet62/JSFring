package fr.pinguet62.dictionary.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import fr.pinguet62.dictionary.filter.PaginationFilter;
import fr.pinguet62.dictionary.filter.PaginationResult;
import fr.pinguet62.dictionary.model.Profile;

/** Tests for pagination filters. */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
@DatabaseSetup("/dataset.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
@Transactional
public class PanigationTest {

    @Autowired
    private ProfileDao profileDao;

    /**
     * All items are selected:
     * <ul>
     * <li>first page</li>
     * <li>the number of item is equals to the total count.</li>
     * </ul>
     */
    @Test
    public void test_all() {
        assertEquals(2, profileDao.count());

        PaginationFilter filter = new PaginationFilter();
        filter.setPage(1);
        filter.setNumberPerPage(2);
        PaginationResult<Profile> result = profileDao.find(filter);

        assertEquals(2, result.getItems().size());
        assertEquals(2, result.getTotalCount());
    }

    /** The result for a page too large must be empty. */
    @Test
    public void test_empty() {
        PaginationFilter filter = new PaginationFilter();
        filter.setPage(99);
        filter.setNumberPerPage(10);
        PaginationResult<Profile> result = profileDao.find(filter);
        assertEquals(0, result.getItems().size());
        assertEquals(2, result.getTotalCount());
    }

    /** Check that the first page is correct. */
    @Test
    public void test_first() {
        // prepare
        for (int i = 3; i <= 25; i++)
            profileDao.create(new Profile(String.valueOf(i)));
        assertEquals(25, profileDao.getAll().size());

        PaginationFilter filter = new PaginationFilter();
        filter.setPage(1);
        filter.setNumberPerPage(10);
        PaginationResult<Profile> result = profileDao.find(filter);
        List<Profile> profiles = result.getItems();
        assertEquals(1, profiles.get(0).getId().intValue());
        assertEquals(2, profiles.get(1).getId().intValue());
        assertTrue(profiles.get(2).getId().intValue() > 3);
    }

    /** Check that the last page (for a non-multiple count) is not full. */
    @Test
    public void test_lastNotFull() {
        // prepare
        for (int i = 3; i <= 25; i++)
            profileDao.create(new Profile(String.valueOf(i)));
        assertEquals(25, profileDao.getAll().size());

        PaginationFilter filter = new PaginationFilter();
        filter.setPage(3);
        filter.setNumberPerPage(10);
        PaginationResult<Profile> result = profileDao.find(filter);
        assertEquals(5, result.getItems().size());
        assertEquals(25, result.getTotalCount());
    }

}
