package fr.pinguet62.dictionary.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.persistence.FetchType;
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

import fr.pinguet62.dictionary.model.Description;
import fr.pinguet62.dictionary.model.Keyword;
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
    private DescriptionDao descriptionDao;

    @Autowired
    private KeywordDao keywordDao;

    @Autowired
    private LanguageDao languageDao;

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private RightDao rightDao;

    /** @see ManyToMany */
    @Test
    public void test_manyToMany_add() {
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
    public void test_manyToMany_lazyLoad() {
        Profile profile = profileDao.get(1);
        assertEquals(3, profile.getRights().size());
    }

    /** @see ManyToOne */
    @Test
    public void test_manyToOne() {
        fail("Not yet implemented");
    }

    /** @see OneToMany */
    @Test
    public void test_oneToMany_add() {
        Keyword keyword = keywordDao.get(1);
        long initialCount = keyword.getDescriptions().size();
        // add
        keyword.getDescriptions().add(
                new Description(languageDao.get("fr"), "Title", "Content"));
        keywordDao.update(keyword);
        // test
        assertEquals(initialCount + 1, keywordDao.get(1).getDescriptions()
                .size());
    }

    /**
     * @see OneToMany
     * @see FetchType#LAZY
     */
    @Test
    public void test_oneToMany_lazyLoad() {
        Keyword keyword = keywordDao.get(1);
        assertEquals(2, keyword.getDescriptions().size());
    }

    /** @see OneToMany */
    @Test
    public void test_oneToMany_remove() {
        Keyword keyword = keywordDao.get(1);
        long initialCount = keyword.getDescriptions().size();
        // remove
        keyword.getDescriptions().remove(
                keyword.getDescriptions().iterator().next());
        keywordDao.update(keyword);
        // test
        assertEquals(initialCount - 1, keywordDao.get(1).getDescriptions()
                .size());
    }

    /** @see OneToOne */
    @Test
    public void test_oneToOne() {
        fail("Not yet implemented");
    }

}
