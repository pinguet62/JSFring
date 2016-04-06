package fr.pinguet62.jsfring.dao.sql;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import fr.pinguet62.jsfring.dao.sql.common.CommonRepositoryImpl;

@Configuration
@EnableJpaRepositories(repositoryBaseClass = CommonRepositoryImpl.class)
public class SqlRepositoryConfig {}