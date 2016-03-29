package fr.pinguet62.jsfring.batch.user;

import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.model.sql.User;

@Component
public class UserWriter extends RepositoryItemWriter<User> {}