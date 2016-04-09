package fr.pinguet62.jsfring.batch.user;

import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.batch.CrudRepositoryItemWriter;
import fr.pinguet62.jsfring.model.sql.User;

@Component
public class UserWriter extends CrudRepositoryItemWriter<User> {}