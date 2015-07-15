package fr.pinguet62.jsfring.service;

import javax.inject.Inject;
import javax.inject.Named;

import fr.pinguet62.jsfring.dao.LanguageDao;
import fr.pinguet62.jsfring.model.Language;

/** The service for {@link Language}. */
@Named
public class LanguageService extends AbstractService<Language, String> {

    @Inject
    protected LanguageService(LanguageDao dao) {
        super(dao);
    }

}
