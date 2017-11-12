package fr.pinguet62.jsfring.batch.admin.config;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@ImportResource({
        "classpath:/org/springframework/batch/admin/web/resources/webapp-config.xml",
        "classpath:/org/springframework/batch/admin/web/resources/servlet-config.xml"})
public class BatchAdminConfig {

    /**
     * TODO javadoc
     * <p>
     * Use {@link Qualifier} because two {@link DataSource} is defined: the <i>Spring Batch admin</i> and <i>business
     * application</i><br>
     * {@code "dataSource"} is the {@link Bean#name()} of <i>Spring Batch admin</i> {@link DataSource}.
     */
    @Bean
    public BatchConfigurer configurer(@Qualifier("dataSource") DataSource dataSource) {
        return new DefaultBatchConfigurer(dataSource);
    }

    /**
     * Because the <i>Spring Batch admin</i> application define a separated {@link DataSource}, the <b>business application</b>
     * {@link DataSource} must be defined as {@link Primary}.
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

}