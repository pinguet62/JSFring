package fr.pinguet62.jsfring.dao;

import org.springframework.stereotype.Repository;

import com.mysema.query.types.Expression;

import fr.pinguet62.jsfring.model.Keyword;
import fr.pinguet62.jsfring.model.QKeyword;

/** The DAO for {@link Keyword}. */
@Repository
public final class KeywordDao extends AbstractDao<Keyword, Integer> {

    @Override
    protected Expression<Keyword> getBaseExpression() {
        return QKeyword.keyword;
    }

}
