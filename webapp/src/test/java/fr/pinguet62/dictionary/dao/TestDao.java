package fr.pinguet62.dictionary.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.pinguet62.dictionary.model.Language;

/** Tests for {@link LanguageDao}. */
public final class TestDao {

	/** The DAO of {@link Language}. */
	private final LanguageDao dao = new LanguageDao();

	/**
	 * Terminate the tests:
	 * <ul>
	 * <li>Clean table: delete created items.</li>
	 * </ul>
	 */
	@After
	public void after() {
		dao.deleteAll();
		assertEquals(0, dao.list().size());
	}

	/**
	 * Initialize the tests:
	 * <ul>
	 * <li>
	 * Clean table: begin test with empty table.</li>
	 * </ul>
	 */
	@Before
	public void before() {
		dao.deleteAll();
		assertEquals(0, dao.list().size());
	}

	@Test
	public void testCreate() {
		// test
		Language language = new Language("FR", "Français");
		dao.create(language);
		assertEquals(1, dao.list().size());
	}

	@Test
	public void testDelete() {
		// init
		Language language1 = new Language("FR", "Français");
		dao.create(language1);
		Language language2 = new Language("EN", "English");
		dao.create(language2);
		assertEquals(2, dao.list().size());

		// test
		dao.delete(dao.get("FR"));
		assertEquals(1, dao.list().size());
		dao.delete(dao.get("EN"));
		assertEquals(0, dao.list().size());
	}

	@Test
	public void testDeleteAll() {
		// init
		Language language1 = new Language("FR", "Français");
		dao.create(language1);
		Language language2 = new Language("EN", "English");
		dao.create(language2);
		assertEquals(2, dao.list().size());

		// test
		dao.deleteAll();
		assertEquals(0, dao.list().size());
	}

	@Test
	public void testGet() {
		// init
		Language wLanguage = new Language("FR", "Français");
		dao.create(wLanguage);
		assertEquals(1, dao.list().size());

		// test
		Language rLanguage = dao.get("FR");
		assertEquals("Français", rLanguage.getName());
	}

	@Test
	public void testList() {
		// init
		Language language1 = new Language("FR", "Français");
		dao.create(language1);
		Language language2 = new Language("EN", "English");
		dao.create(language2);
		assertEquals(2, dao.list().size());

		// test
		List<Language> languages = dao.list();
		assertEquals(2, languages.size());
	}

	@Test
	public void testUpdate() {
		// init
		Language language = new Language("FR", "Français");
		dao.create(language);

		// test
		language.setName("frensé");
		dao.update(language);
		assertEquals("frensé", dao.get("FR").getName());
	}

}
