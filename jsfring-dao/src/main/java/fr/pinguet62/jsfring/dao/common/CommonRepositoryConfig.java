package fr.pinguet62.jsfring.dao.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// TODO @EnableJpaRepositories: dynamic basePackages
@Configuration
@EnableJpaRepositories(basePackages = "fr.pinguet62.jsfring", repositoryBaseClass = CommonRepositoryImpl.class)
public class CommonRepositoryConfig {}