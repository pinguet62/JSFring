package fr.pinguet62.jsfring.dao;

import org.springframework.stereotype.Repository;

import com.mysema.query.types.Expression;

import fr.pinguet62.jsfring.model.QRight;
import fr.pinguet62.jsfring.model.Right;

/** The DAO for {@link Right}. */
@Repository
public final class RightDao extends AbstractDao<Right, String> {

    @Override
    protected Expression<Right> getBaseExpression() {
        return QRight.right;
    }

}
