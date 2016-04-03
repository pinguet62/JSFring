package fr.pinguet62.jsfring.batch.user;

import javax.inject.Inject;

import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.model.sql.User;

@Component
public class UserWriter extends RepositoryItemWriter<User> {

    public UserWriter() {
        setMethodName("save");
    }

    @Override
    @Inject
    public void setRepository(CrudRepository<User, ?> repository) {
        super.setRepository(repository);
    }

}