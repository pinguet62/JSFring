package fr.pinguet62.jsfring.dao.sample;

import javax.inject.Named;

import com.mysema.query.types.Expression;

import fr.pinguet62.jsfring.dao.AbstractDao;
import fr.pinguet62.jsfring.model.QDescription;
import fr.pinguet62.jsfring.model.sample.Description;

/** The DAO for {@link Description}. */
@Named
public final class DescriptionDao extends AbstractDao<Description, Integer> {

    @Override
    protected Expression<Description> getBaseExpression() {
        return QDescription.description;
    }

}