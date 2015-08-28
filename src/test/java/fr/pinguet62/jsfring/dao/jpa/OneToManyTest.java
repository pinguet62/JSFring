package fr.pinguet62.jsfring.dao.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import fr.pinguet62.Config;
import fr.pinguet62.jsfring.dao.sample.KeywordDao;
import fr.pinguet62.jsfring.dao.sample.LanguageDao;
import fr.pinguet62.jsfring.model.sample.Description;
import fr.pinguet62.jsfring.model.sample.Keyword;

/** @see OneToMany */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = Config.SPRING)
@DatabaseSetup(Config.DATASET)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@Transactional
public class OneToManyTest {

    @Inject
    private KeywordDao keywordDao;

    @Inject
    private LanguageDao languageDao;

    /**
     * When new element is {@link List#add(Object) added} to relationship, the
     * {@link List} of objects must be updated.
     */
    @Test
    public void test_add() {
        final int id = 1;

        // Before
        Keyword keyword = keywordDao.get(id);
        long initialCount = keyword.getDescriptions().size();

        keyword.getDescriptions().add(
                new Description(languageDao.get("fr"), "Title", "Content"));
        keywordDao.update(keyword);

        // Test
        long newCount = keywordDao.get(id).getDescriptions().size();
        assertEquals(initialCount + 1, newCount);
    }

    /**
     * The access to relationship must fetch the {@link List} of associated
     * objects.
     *
     * @see FetchType#LAZY
     */
    @Test
    public void test_lazyLoad() {
        Keyword keyword = keywordDao.get(1);

        assertEquals(2, keyword.getDescriptions().size());
    }

    /**
     * When element is {@link List#remove(Object) removed} from relationship,
     * the {@link List} of objects must be updated.
     */
    @Test
    public void test_remove() {
        final int id = 1;

        // Before
        Keyword keyword = keywordDao.get(id);
        long initialCount = keyword.getDescriptions().size();

        keyword.getDescriptions().remove(
                keyword.getDescriptions().iterator().next());
        keywordDao.update(keyword);

        // Test
        long newCount = keywordDao.get(1).getDescriptions().size();
        assertEquals(initialCount - 1, newCount);
    }

    /** When element of relationship is updated, the object must be updated. */
    @Test
    public void test_update() {
        final int id = 1;

        // Before
        Keyword keyword = keywordDao.get(id);
        Description description = keyword.getDescriptions().stream()
                .filter(d -> d.getLanguage().getCode().equals("fr")).findAny()
                .get();
        String initialValue = description.getContent();

        String newValue = "Le Java c'est la vie !";
        assertNotEquals(newValue, initialValue);
        description.setContent(newValue);
        keywordDao.update(keyword);

        // Test
        assertEquals(newValue, keywordDao.get(id).getDescriptions().stream()
                .filter(d -> d.getLanguage().getCode().equals("fr")).findAny()
                .get().getContent());
    }

}