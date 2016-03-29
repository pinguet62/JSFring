package fr.pinguet62.jsfring.batch.user;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import fr.pinguet62.jsfring.model.sql.User;

public class UserProcessor implements ItemProcessor<UserRow, User> {

    private static final Logger LOGGER = getLogger(UserProcessor.class);

    @Override
    public User process(UserRow input) throws Exception {
        LOGGER.debug("process: " + input.getLogin());
        User output = new User();
        output.setLogin(input.getLogin());
        output.setPassword(input.getPassword());
        output.setEmail(input.getEmail());
        return output;
    }

}