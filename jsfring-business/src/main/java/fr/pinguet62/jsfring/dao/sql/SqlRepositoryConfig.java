package fr.pinguet62.jsfring.dao.sql;

import fr.pinguet62.jsfring.dao.sql.common.CommonRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(repositoryBaseClass = CommonRepositoryImpl.class)
public class SqlRepositoryConfig {
}