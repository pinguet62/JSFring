package fr.pinguet62.dictionary.dao.relationship;

import static org.junit.Assert.assertEquals;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;

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

import fr.pinguet62.Config;
import fr.pinguet62.dictionary.dao.KeywordDao;
import fr.pinguet62.dictionary.dao.LanguageDao;
import fr.pinguet62.dictionary.model.Description;
import fr.pinguet62.dictionary.model.Keyword;

/**
 * Tests for "one to many" associations of database tables.
 *
 * @see OneToMany
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@Transactional
public class OneToManyTest {

    @Autowired
    private KeywordDao keywordDao;

    @Autowired
    private LanguageDao languageDao;

    /**
     * Add new item to the collection.
     * <p>
     * When the base object is updated, the new item must be automatically
     * inserted.
     */
    @Test
    public void test_add() {
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

    /** @see FetchType#LAZY */
    @Test
    public void test_lazyLoad() {
        Keyword keyword = keywordDao.get(1);
        assertEquals(2, keyword.getDescriptions().size());
    }

    /**
     * Remove an item from the collection.
     * <p>
     * When the base object is updated, the old item must be automatically
     * removed.
     */
    @Test
    public void test_remove() {
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

    /**
     * Update an item of the collection.
     * <p>
     * When an item is modified, this item is updated.
     */
    @Test
    public void test_update() {
        Keyword keyword = keywordDao.get(1);
        Description description = keyword.getDescriptions().stream()
                .filter(d -> d.getLanguage().getCode().equals("fr")).findAny()
                .get();
        assertEquals("Langage de programmation.", description.getContent());
        // update
        description.setContent("Le Java c'est la vie !");
        keywordDao.update(keyword);
        // test
        assertEquals(
                "Le Java c'est la vie !",
                keywordDao.get(1).getDescriptions().stream()
                        .filter(d -> d.getLanguage().getCode().equals("fr"))
                        .findAny().get().getContent());
    }

}
