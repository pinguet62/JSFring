package fr.pinguet62.dictionary.dao;

import static org.junit.Assert.assertEquals;

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
     * Find all items.
     * <p>
     * The first index is {@code 0} and the page size is greater than the total
     * count.
     */
    @Test
    public void test_all() {
        final int totalCount = 2;
        assertEquals(totalCount, profileDao.count());

        PaginationFilter filter = new PaginationFilter();
        filter.setFirst(0);
        filter.setNumberPerPage(totalCount);
        PaginationResult<Profile> result = profileDao.find(filter);

        // total count
        assertEquals(totalCount, result.getTotalCount());
        // values
        List<Profile> profiles = result.getItems();
        assertEquals(totalCount, profiles.size());
    }

    /** Test that the first page is correct. */
    @Test
    public void test_firstPage() {
        final int totalCount = 25;
        final int pageSize = 5;

        // prepare
        for (int i = 2; i < totalCount; i++)
            profileDao.create(new Profile("title " + i));
        assertEquals(totalCount, profileDao.getAll().size());

        PaginationFilter filter = new PaginationFilter();
        filter.setFirst(0);
        filter.setNumberPerPage(pageSize);
        PaginationResult<Profile> result = profileDao.find(filter);

        // total count
        assertEquals(totalCount, result.getTotalCount());
        // values
        List<Profile> profiles = result.getItems();
        assertEquals(1, profiles.get(0).getId().intValue());
        assertEquals(2, profiles.get(1).getId().intValue());
        assertEquals("title 2", profiles.get(2).getTitle());
        assertEquals("title 3", profiles.get(3).getTitle());
        assertEquals("title 4", profiles.get(4).getTitle());
        assertEquals(pageSize, profiles.size());
    }

    /**
     * Index too large: no result found.
     * <p>
     * They are 2 elements, and the result for item from {@code first=90} to
     * {@code first+numberPerPage=90+5=95} index must be empty.
     */
    @Test
    public void test_indexTooLarges() {
        PaginationFilter filter = new PaginationFilter();
        filter.setFirst(90);
        filter.setNumberPerPage(5);
        PaginationResult<Profile> result = profileDao.find(filter);

        // total count
        assertEquals(2, result.getTotalCount());
        // values
        List<Profile> profiles = result.getItems();
        assertEquals(0, profiles.size());
    }

    /**
     * Check that the last page is not full.
     * <p>
     * The total count is not a multiple of the page size.
     */
    @Test
    public void test_lastPage() {
        final int totalCount = 25;

        // prepare
        for (int i = 2; i < totalCount; i++)
            profileDao.create(new Profile("title " + i));
        assertEquals(totalCount, profileDao.getAll().size());

        PaginationFilter filter = new PaginationFilter();
        filter.setFirst(20);
        filter.setNumberPerPage(10);
        PaginationResult<Profile> result = profileDao.find(filter);

        // total count
        assertEquals(totalCount, result.getTotalCount());
        // values
        List<Profile> profiles = result.getItems();
        assertEquals("title 20", profiles.get(0).getTitle());
        assertEquals("title 21", profiles.get(1).getTitle());
        assertEquals("title 22", profiles.get(2).getTitle());
        assertEquals("title 23", profiles.get(3).getTitle());
        assertEquals("title 24", profiles.get(4).getTitle());
        assertEquals(5, profiles.size());
    }

}
