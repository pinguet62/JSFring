package fr.pinguet62.dictionary.dao;

import org.springframework.stereotype.Repository;

import com.mysema.query.types.Expression;

import fr.pinguet62.dictionary.model.Profile;
import fr.pinguet62.dictionary.model.QProfile;

/** The DAO for {@link Profile}. */
@Repository
public final class ProfileDao extends AbstractDao<Profile, Integer> {

    @Override
    protected Expression<Profile> getBaseExpression() {
        return QProfile.profile;
    }

}