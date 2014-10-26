package fr.pinguet62.dictionary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.pinguet62.dictionary.dao.LanguageDao;
import fr.pinguet62.dictionary.model.Language;

/** The service for {@link Language}. */
@Service
public class LanguageService extends AbstractService<Language, String> {

    @Autowired
    protected LanguageService(LanguageDao dao) {
        super(dao);
    }

}
