package fr.pinguet62.jsfring.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:/org/springframework/batch/admin/web/resources/webapp-config.xml",
        "classpath:/org/springframework/batch/admin/web/resources/servlet-config.xml" })
public class BatchAdminConfig {

    // @Override
    // @Autowired
    // public void setDataSource(@Qualifier(DATABASE) DataSource dataSource) {
    // super.setDataSource(dataSource);
    // }

    @Bean
    BatchConfigurer configurer(@Qualifier("batchDataSource") DataSource dataSource) {
        return new DefaultBatchConfigurer(dataSource);
    }

}