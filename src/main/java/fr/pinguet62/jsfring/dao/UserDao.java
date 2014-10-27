package fr.pinguet62.jsfring.dao;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Expression;

import fr.pinguet62.jsfring.model.QUser;
import fr.pinguet62.jsfring.model.User;

/** The DAO for {@link User}. */
@Repository
public final class UserDao extends AbstractDao<User, String> {

    @Override
    protected Expression<User> getBaseExpression() {
        return QUser.user;
    }

    /**
     * Get the {@link User} by email.
     *
     * @param email
     *            The user's email.
     * @return The {@link User}, {@code null} if not found.
     */
    public User getByEmail(String email) {
        return new JPAQuery(em).from(QUser.user)
                .where(QUser.user.email.eq(email)).singleResult(QUser.user);
    }

}
