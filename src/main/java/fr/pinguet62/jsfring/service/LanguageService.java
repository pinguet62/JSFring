package fr.pinguet62.jsfring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.pinguet62.jsfring.dao.LanguageDao;
import fr.pinguet62.jsfring.model.Language;

/** The service for {@link Language}. */
@Service
public class LanguageService extends AbstractService<Language, String> {

    @Autowired
    protected LanguageService(LanguageDao dao) {
        super(dao);
    }

}
