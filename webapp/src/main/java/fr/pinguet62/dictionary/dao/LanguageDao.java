package fr.pinguet62.dictionary.dao;

import org.springframework.stereotype.Repository;

import com.mysema.query.types.Expression;

import fr.pinguet62.dictionary.model.Language;
import fr.pinguet62.dictionary.model.QLanguage;

/** The DAO for {@link Language}. */
@Repository
public final class LanguageDao extends AbstractDao<Language, String> {

    @Override
    protected Expression<Language> getBaseExpression() {
        return QLanguage.language;
    }

}
