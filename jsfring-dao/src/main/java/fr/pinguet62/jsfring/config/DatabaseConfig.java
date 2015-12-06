package fr.pinguet62.jsfring.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DatabaseConfig {

    private URI dbUrl;

    @Inject
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(org.postgresql.Driver.class.getName());
        dataSource.setUrl("jdbc:postgresql://" + dbUrl.getHost() + ':' + dbUrl.getPort() + dbUrl.getPath() + '?'
                + environment.getProperty("DATABASE_URL_PARAMETERS"));
        dataSource.setUsername(dbUrl.getUserInfo().split(":")[0]);
        dataSource.setPassword(dbUrl.getUserInfo().split(":")[1]);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        // entityManagerFactoryBean.setPersistenceUnitName("default");
        entityManagerFactoryBean.setPackagesToScan("fr.pinguet62.jsfring");
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return entityManagerFactoryBean;
    }

    @PostConstruct
    private void init() throws URISyntaxException {
        dbUrl = new URI(environment.getProperty("DATABASE_URL"));
    }

    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        Properties properties = new Properties();
        // properties.put("hibernate.dialect",
        // org.hibernate.dialect.PostgreSQL92Dialect.class);
        properties.put("hibernate.databasePlatform", org.hibernate.dialect.PostgreSQL92Dialect.class);
        properties.put("hibernate.globally_quoted_identifiers", false);
        properties.put("hibernate.default_schema", "public");
        properties.put("hibernate.use_sql_comments", true);
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        sessionFactory.setHibernateProperties(properties);
        return sessionFactory.getObject();
    }

}