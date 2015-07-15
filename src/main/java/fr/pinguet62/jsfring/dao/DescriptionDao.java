package fr.pinguet62.jsfring.dao;

import javax.inject.Named;

import com.mysema.query.types.Expression;

import fr.pinguet62.jsfring.model.Description;
import fr.pinguet62.jsfring.model.QDescription;

/** The DAO for {@link Description}. */
@Named
public final class DescriptionDao extends AbstractDao<Description, Integer> {

    @Override
    protected Expression<Description> getBaseExpression() {
        return QDescription.description;
    }

}