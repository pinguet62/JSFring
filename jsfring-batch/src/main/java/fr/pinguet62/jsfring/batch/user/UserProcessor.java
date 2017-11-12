package fr.pinguet62.jsfring.batch.user;

import fr.pinguet62.jsfring.dao.sql.UserDao;
import fr.pinguet62.jsfring.model.sql.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * {@link ItemProcessor} who convert, input {@link UserRow}, to {@link User} initialized for {@link UserDao#save(User) database
 * insert}.
 */
@Slf4j
@Component
public class UserProcessor implements ItemProcessor<UserRow, User> {

    @Override
    public User process(UserRow input) {
        log.debug("process: " + input.getEmail());
        User output = new User();
        output.setEmail(input.getEmail());
        output.setPassword(input.getPassword());
        output.setActive(true);
        return output;
    }

}