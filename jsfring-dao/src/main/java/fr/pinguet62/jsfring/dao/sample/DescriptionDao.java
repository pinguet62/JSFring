package fr.pinguet62.jsfring.dao.sample;

import org.springframework.stereotype.Repository;

import com.mysema.query.types.Expression;

import fr.pinguet62.jsfring.dao.AbstractDao;
import fr.pinguet62.jsfring.model.sample.Description;
import fr.pinguet62.jsfring.model.sample.QDescription;

/** The DAO for {@link Description}. */
@Repository
public class DescriptionDao extends AbstractDao<Description, Integer> {

    @Override
    protected Expression<Description> getBaseExpression() {
        return QDescription.description;
    }

}