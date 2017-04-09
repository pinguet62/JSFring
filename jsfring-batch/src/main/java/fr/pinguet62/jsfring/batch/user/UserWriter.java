package fr.pinguet62.jsfring.batch.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.batch.common.CrudRepositoryItemWriter;
import fr.pinguet62.jsfring.model.sql.User;

@Component
public class UserWriter extends CrudRepositoryItemWriter<User> {

    public UserWriter(CrudRepository<User, ?> repository) {
        super(repository);
    }

}