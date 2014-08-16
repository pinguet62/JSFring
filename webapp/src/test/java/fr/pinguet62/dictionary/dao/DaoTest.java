package fr.pinguet62.dictionary.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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

import fr.pinguet62.dictionary.model.Language;

/** Tests for {@link AbstractDao}. */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
@DatabaseSetup("/dataset.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
@Transactional
public final class DaoTest {

    /** The {@link AbstractDao} to test. */
    @Autowired
    private LanguageDao dao;

    /** Test for {@link AbstractDao#create(Object)}. */
    @Test
    public void test_create() {
        assertEquals(3, dao.count());
        dao.create(new Language("es", "Español"));
        assertEquals(4, dao.count());
        dao.create(new Language("cr", "ᓀᐦᐃᔭᐍᐏᐣ"));
        assertEquals(5, dao.count());
    }

    /** Test for {@link AbstractDao#delete(Object)} */
    @Test
    public void test_delete() {
        assertEquals(3, dao.count());
        dao.delete(dao.get("fr"));
        assertEquals(2, dao.count());
        dao.delete(dao.get("en"));
        assertEquals(1, dao.count());
        dao.delete(dao.get("ar"));
        assertEquals(0, dao.count());
    }

    /** Test for {@link AbstractDao#deleteAll()}. */
    @Test
    public void test_deleteAll() {
        assertEquals(3, dao.count());
        // not empty
        dao.deleteAll();
        assertEquals(0, dao.count());
        // already empty
        dao.deleteAll();
    }

    /** Test for {@link AbstractDao#get(java.io.Serializable)}. */
    @Test
    public void test_get() {
        // existing
        Language frLanguage = dao.get("fr");
        assertEquals("Français", frLanguage.getName());
        Language enLanguage = dao.get("en");
        assertEquals("English", enLanguage.getName());
        Language arLanguage = dao.get("ar");
        assertEquals("العربية", arLanguage.getName());
        // not existing
        assertNull(dao.get("??"));
    }

    /** Test for {@link AbstractDao#list()}. */
    @Test
    public void test_list() {
        List<Language> languages = dao.list();
        assertEquals(3, languages.size());
    }

    /** Test for {@link AbstractDao#update(Object)}. */
    @Test
    public void test_update() {
        Language language = dao.get("fr");
        // update
        language.setName("frensé");
        dao.update(language);
        // test
        assertEquals("frensé", dao.get("fr").getName());
    }

}
