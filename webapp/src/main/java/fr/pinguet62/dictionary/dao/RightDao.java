package fr.pinguet62.dictionary.dao;

import org.springframework.stereotype.Repository;

import com.mysema.query.types.Expression;

import fr.pinguet62.dictionary.model.QRight;
import fr.pinguet62.dictionary.model.Right;

/** The DAO for {@link Right}. */
@Repository
public final class RightDao extends AbstractDao<Right, String> {

    @Override
    protected Expression<Right> getBaseExpression() {
        return QRight.right;
    }

}
