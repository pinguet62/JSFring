package fr.pinguet62.jsfring.dao.sample;

import javax.inject.Named;

import com.mysema.query.types.Expression;

import fr.pinguet62.jsfring.dao.AbstractDao;
import fr.pinguet62.jsfring.model.QLanguage;
import fr.pinguet62.jsfring.model.sample.Language;

/** The DAO for {@link Language}. */
@Named
public final class LanguageDao extends AbstractDao<Language, String> {

    @Override
    protected Expression<Language> getBaseExpression() {
        return QLanguage.language;
    }

}