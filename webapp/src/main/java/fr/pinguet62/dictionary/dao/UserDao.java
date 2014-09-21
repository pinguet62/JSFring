package fr.pinguet62.dictionary.dao;

import org.springframework.stereotype.Repository;

import com.mysema.query.types.Expression;

import fr.pinguet62.dictionary.model.QUser;
import fr.pinguet62.dictionary.model.User;

/** The DAO for {@link User}. */
@Repository
public final class UserDao extends AbstractDao<User, String> {

    @Override
    protected Expression<User> getBaseExpression() {
        return QUser.user;
    }

}
