package fr.pinguet62.jsfring.dao.sample;

import javax.inject.Named;

import com.mysema.query.types.Expression;

import fr.pinguet62.jsfring.dao.AbstractDao;
import fr.pinguet62.jsfring.model.QKeyword;
import fr.pinguet62.jsfring.model.sample.Keyword;

/** The DAO for {@link Keyword}. */
@Named
public final class KeywordDao extends AbstractDao<Keyword, Integer> {

    @Override
    protected Expression<Keyword> getBaseExpression() {
        return QKeyword.keyword;
    }

}