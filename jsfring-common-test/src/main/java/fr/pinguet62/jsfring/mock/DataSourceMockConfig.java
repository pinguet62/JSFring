package fr.pinguet62.jsfring.mock;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

@Configuration
public class DataSourceMockConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(HSQL).setScriptEncoding(UTF_8.toString())
                .ignoreFailedDrops(true).build();
    }

}