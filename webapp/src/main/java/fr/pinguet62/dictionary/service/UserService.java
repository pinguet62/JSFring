package fr.pinguet62.dictionary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.pinguet62.dictionary.dao.UserDao;
import fr.pinguet62.dictionary.model.User;

/** The service for {@link User}. */
@Service
public final class UserService extends AbstractService<User, String> {

    @Autowired
    protected UserService(UserDao dao) {
        super(dao);
    }

}
