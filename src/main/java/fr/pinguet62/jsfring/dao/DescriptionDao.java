package fr.pinguet62.jsfring.dao;

import org.springframework.stereotype.Repository;

import com.mysema.query.types.Expression;

import fr.pinguet62.jsfring.model.Description;
import fr.pinguet62.jsfring.model.QDescription;

/** The DAO for {@link Description}. */
@Repository
public final class DescriptionDao extends AbstractDao<Description, Integer> {

    @Override
    protected Expression<Description> getBaseExpression() {
        return QDescription.description;
    }

}
