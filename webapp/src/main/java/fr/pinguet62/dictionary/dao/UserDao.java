package fr.pinguet62.dictionary.dao;

import org.springframework.stereotype.Repository;

import fr.pinguet62.dictionary.model.User;

/** The DAO for {@link User}. */
@Repository
public final class UserDao extends AbstractDao<User, String> {
}
