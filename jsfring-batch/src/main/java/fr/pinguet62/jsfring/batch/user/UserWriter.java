package fr.pinguet62.jsfring.batch.user;

import fr.pinguet62.jsfring.batch.common.CrudRepositoryItemWriter;
import fr.pinguet62.jsfring.model.sql.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class UserWriter extends CrudRepositoryItemWriter<User> {

    public UserWriter(CrudRepository<User, ?> repository) {
        super(repository);
    }

}