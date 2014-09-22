package fr.pinguet62.dictionary.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.primefaces.model.SortOrder;
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
import fr.pinguet62.dictionary.model.Profile;
import fr.pinguet62.dictionary.model.QProfile;
import fr.pinguet62.dictionary.service.ProfileService;

/** @see QuerydslLazyDataModel */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
@DatabaseSetup("/dataset.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
@Transactional
public class QuerydslLazyDataModelTest {

    /** The number of objects for tests. */
    private static final int NB = 12;

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private ProfileService profileService;

    /**
     * Clean data, and create {@link #NB} new {@link Profile} who title is the
     * index: {@code Profile("0")}, {@code Profile("1")}, ...,
     * {@code Profile("11")}.
     */
    @Before
    public void before() {
        profileDao.deleteAll();
        for (int i = 0; i < NB; i++)
            profileDao.create(new Profile(String.valueOf(i)));
    }

    /**
     * Create new {@link QuerydslLazyDataModel} and invoke the lazy loading
     * method.
     * <p>
     * No sort or filter applied.
     *
     * @return The result.
     * @see QuerydslLazyDataModel#load(int, int, String, SortOrder, Map)
     */
    private List<Profile> getLoadResult(int first, int pageSize) {
        return new QuerydslLazyDataModel<Profile>(profileService,
                QProfile.profile).load(first, pageSize, null, null,
                        new HashMap<String, Object>());
    }

    /**
     * Find all items: the page size is greater than the total count.
     *
     * @see QuerydslLazyDataModel#load(int, int, String,
     *      org.primefaces.model.SortOrder, java.util.Map)
     */
    @Test
    public void test_pagination_all() {
        assertEquals(NB, getLoadResult(0, 999).size());
    }

    /**
     * First item index greater than the total count: no result found.
     *
     * @see QuerydslLazyDataModel#load(int, int, String,
     *      org.primefaces.model.SortOrder, java.util.Map)
     */
    @Test
    public void test_pagination_firstItemTooLarge() {
        assertTrue(getLoadResult(999, 5).isEmpty());
    }

    /**
     * The first page contains the first objects on database.<br/>
     * No sort or filter applied.
     *
     * @see QuerydslLazyDataModel#load(int, int, String,
     *      org.primefaces.model.SortOrder, java.util.Map)
     */
    @Test
    public void test_pagination_firstPage() {
        List<Profile> profiles = getLoadResult(0, 5);

        assertEquals(5, profiles.size());
        assertEquals("0", profiles.get(0).getTitle());
        assertEquals("1", profiles.get(1).getTitle());
        assertEquals("2", profiles.get(2).getTitle());
        assertEquals("3", profiles.get(3).getTitle());
        assertEquals("4", profiles.get(4).getTitle());
    }

    /**
     * The last page is not full.<br/>
     * No sort or filter applied.
     *
     * @see QuerydslLazyDataModel#load(int, int, String,
     *      org.primefaces.model.SortOrder, java.util.Map)
     */
    @Test
    public void test_pagination_lastPage() {
        List<Profile> profiles = getLoadResult(10, 5);

        assertEquals(2, profiles.size());
        assertEquals("10", profiles.get(0).getTitle());
        assertEquals("11", profiles.get(1).getTitle());
    }

}
