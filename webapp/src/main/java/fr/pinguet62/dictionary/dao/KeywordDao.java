package fr.pinguet62.dictionary.dao;

import org.springframework.stereotype.Repository;

import com.mysema.query.types.Expression;

import fr.pinguet62.dictionary.model.Keyword;
import fr.pinguet62.dictionary.model.QKeyword;

/** The DAO for {@link Keyword}. */
@Repository
public final class KeywordDao extends AbstractDao<Keyword, Integer> {

    @Override
    protected Expression<Keyword> getBaseExpression() {
        return QKeyword.keyword;
    }

}
