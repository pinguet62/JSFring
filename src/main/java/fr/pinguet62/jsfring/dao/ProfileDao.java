package fr.pinguet62.jsfring.dao;

import javax.inject.Named;

import com.mysema.query.types.Expression;

import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.model.QProfile;

/** The DAO for {@link Profile}. */
@Named
public final class ProfileDao extends AbstractDao<Profile, Integer> {

    @Override
    protected Expression<Profile> getBaseExpression() {
        return QProfile.profile;
    }

}