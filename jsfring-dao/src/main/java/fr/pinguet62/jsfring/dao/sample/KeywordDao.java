package fr.pinguet62.jsfring.dao.sample;

import org.springframework.stereotype.Repository;

import com.mysema.query.types.Expression;

import fr.pinguet62.jsfring.dao.AbstractDao;
import fr.pinguet62.jsfring.model.sample.Keyword;
import fr.pinguet62.jsfring.model.sample.QKeyword;

/** The DAO for {@link Keyword}. */
@Repository
public class KeywordDao extends AbstractDao<Keyword, Integer> {

    @Override
    protected Expression<Keyword> getBaseExpression() {
        return QKeyword.keyword;
    }

}