package fr.pinguet62.jsfring.service.sample;

import javax.inject.Inject;
import javax.inject.Named;

import fr.pinguet62.jsfring.dao.sample.LanguageDao;
import fr.pinguet62.jsfring.model.sample.Language;
import fr.pinguet62.jsfring.service.AbstractService;

/** The service for {@link Language}. */
@Named
public class LanguageService extends AbstractService<Language, String> {

    @Inject
    protected LanguageService(LanguageDao dao) {
        super(dao);
    }

}
