package fr.pinguet62.jsfring.batch.config;

import static fr.pinguet62.jsfring.batch.config.BatchDatabase.DATABASE;

import java.util.Properties;

import org.hibernate.dialect.HSQLDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Primary // Use this for batch configuration
@Component(DATABASE)
public class BatchDatabase extends DriverManagerDataSource {

    static final String DATABASE = "batchDataSource";

    public BatchDatabase(@Value("${batch.jdbc.url}") String url, @Value("${batch.jdbc.url}") String user,
            @Value("${batch.jdbc.password}") String password, @Value("${batch.jdbc.driver}") String driverClassName) {
        super(url, user, password);
        setDriverClassName(driverClassName);
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", HSQLDialect.class.getName());
        setConnectionProperties(properties);
    }

}