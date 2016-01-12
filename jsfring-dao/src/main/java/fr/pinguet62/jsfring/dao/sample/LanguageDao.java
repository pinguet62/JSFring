package fr.pinguet62.jsfring.dao.sample;

import org.springframework.stereotype.Repository;

import com.mysema.query.types.Expression;

import fr.pinguet62.jsfring.dao.AbstractDao;
import fr.pinguet62.jsfring.model.sample.Language;
import fr.pinguet62.jsfring.model.sample.QLanguage;

/** The DAO for {@link Language}. */
@Repository
public class LanguageDao extends AbstractDao<Language, String> {

    @Override
    protected Expression<Language> getBaseExpression() {
        return QLanguage.language;
    }

}