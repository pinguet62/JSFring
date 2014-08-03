package fr.pinguet62.dictionary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.pinguet62.dictionary.dao.LanguageDao;
import fr.pinguet62.dictionary.model.Language;

/** The service for {@link Language}. */
@Service
public final class LanguageService {

	/** The {@link Language} DAO. */
	@Autowired
	private LanguageDao dao;

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
