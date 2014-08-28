package fr.pinguet62.dictionary.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.UUID;

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
import fr.pinguet62.dictionary.model.Profile;

/** Tests for {@link AbstractDao}. */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
@DatabaseSetup("/dataset.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@Transactional
public class AbstractDaoTest {

    @Autowired
    private LanguageDao languageDao;

    @Autowired
    private ProfileDao profileDao;

    /** @see AbstractDao#count() */
    @Test
    public void test_count() {
        assertEquals(3, languageDao.count());
    }

    /** @see AbstractDao#create(Object) */
    @Test
    public void test_create() {
        assertEquals(3, languageDao.count());
        languageDao.create(new Language("es", "Español"));
        assertEquals(4, languageDao.count());
        languageDao.create(new Language("cr", "ᓀᐦᐃᔭᐍᐏᐣ"));
        assertEquals(5, languageDao.count());
    }

    /** @see AbstractDao#create(Object) */
    @Test
    public void test_create_autoIncrement() {
        long initialCount = profileDao.count();
        assertEquals(initialCount, profileDao.count());
        profileDao.create(new Profile(UUID.randomUUID().toString()
                .substring(0, 10)));
        assertEquals(initialCount + 1, profileDao.count());
    }

    /** @see AbstractDao#delete(Object) */
    @Test
    public void test_delete() {
        assertEquals(2, profileDao.count());
        profileDao.delete(profileDao.get(1));
        assertEquals(1, profileDao.count());
        profileDao.delete(profileDao.get(2));
        assertEquals(0, profileDao.count());
    }

    /** @see AbstractDao#deleteAll() */
    @Test
    public void test_deleteAll() {
        assertEquals(2, profileDao.count());
        // not empty
        profileDao.deleteAll();
        assertEquals(0, profileDao.count());
        // already empty
        profileDao.deleteAll();
    }

    /** @see AbstractDao#get(java.io.Serializable) */
    @Test
    public void test_get() {
        // existing
        Language frLanguage = languageDao.get("fr");
        assertEquals("Français", frLanguage.getName());
        Language enLanguage = languageDao.get("en");
        assertEquals("English", enLanguage.getName());
        Language arLanguage = languageDao.get("ar");
        assertEquals("العربية", arLanguage.getName());
        // not existing
        assertNull(languageDao.get("??"));
    }

    /** @see AbstractDao#getAll() */
    @Test
    public void test_getAll() {
        List<Language> languages = languageDao.getAll();
        assertEquals(3, languages.size());
    }

    /** @see AbstractDao#update(Object) */
    @Test
    public void test_update() {
        Language language = languageDao.get("fr");
        // update
        language.setName("frensé");
        languageDao.update(language);
        // test
        assertEquals("frensé", languageDao.get("fr").getName());
    }

}
