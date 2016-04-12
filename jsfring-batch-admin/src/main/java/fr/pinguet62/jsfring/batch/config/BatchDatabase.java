package fr.pinguet62.jsfring.batch.config;

import static fr.pinguet62.jsfring.batch.config.BatchDatabase.DATABASE;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.hibernate.dialect.HSQLDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component(DATABASE)
@Primary // Use this for batch configuration
public class BatchDatabase extends DriverManagerDataSource {

    static final String DATABASE = "batchDataSource";

    @Value("${batch.jdbc.driver}")
    private String driverClassName;

    @Value("${batch.jdbc.password}")
    private String password;

    @Value("${batch.jdbc.url}")
    private String url;

    @Value("${batch.jdbc.user}")
    private String user;

    @PostConstruct
    private void init() throws ClassNotFoundException {
        setDriverClassName(driverClassName);
        setUrl(url);
        setUsername(user);
        setPassword(password);
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", HSQLDialect.class.getName());
        setConnectionProperties(properties);
    }

}