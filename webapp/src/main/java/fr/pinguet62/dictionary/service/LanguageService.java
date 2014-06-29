package fr.pinguet62.dictionary.service;

import fr.pinguet62.dictionary.dao.LanguageDao;
import fr.pinguet62.dictionary.model.Language;

/** The service for {@link Language}. */
public final class LanguageService {

	/** The {@link Langage} DAO. */
	private final LanguageDao dao = new LanguageDao();

	/**
	 * Create new {@link Language}.
	 *
	 * @param language
	 *            The {@link Language}.
	 * @return The code.
	 */
	public String create(Language language) {
		return dao.create(language);
	}

}
