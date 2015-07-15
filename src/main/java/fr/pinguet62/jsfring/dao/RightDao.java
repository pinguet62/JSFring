package fr.pinguet62.jsfring.dao;

import javax.inject.Named;

import com.mysema.query.types.Expression;

import fr.pinguet62.jsfring.model.QRight;
import fr.pinguet62.jsfring.model.Right;

/** The DAO for {@link Right}. */
@Named
public final class RightDao extends AbstractDao<Right, String> {

    @Override
    protected Expression<Right> getBaseExpression() {
        return QRight.right;
    }

}