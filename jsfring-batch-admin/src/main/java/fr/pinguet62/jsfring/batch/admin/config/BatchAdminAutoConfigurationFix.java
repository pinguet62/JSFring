package fr.pinguet62.jsfring.batch.admin.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Primary;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;

/**
 * Rename and set as {@link Primary} the Spring Boot {@link DataSource} (implicitly) named {@code dataSource},
 * to avoid collision with Spring Batch Admin {@link DataSource} explicitly named {@code dataSource}.
 * <p>
 * Executed between Spring Boot and Spring Batch Admin.
 * <p>
 * <b>Warning:</b> Works only if {@link DataSource} is only created by Spring Boot autoconfiguration.
 */
@Configuration
@AutoConfigureAfter(HibernateJpaAutoConfiguration.class) // after all dataSource (& co.) initializations
@AutoConfigureBefore(BatchAdminAutoConfiguration.class)
@Import(BatchAdminAutoConfigurationFix.Registrar.class)
public class BatchAdminAutoConfigurationFix {

    static class Registrar implements ImportBeanDefinitionRegistrar {
        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            BeanDefinition beanDefinition = registry.getBeanDefinition("dataSource");
            if (!beanDefinition.getFactoryBeanName().contains(AutoConfigurationPackage.class.getPackage().getName()))
                return;

            registry.removeBeanDefinition("dataSource");
            beanDefinition.setPrimary(true);
            registry.registerBeanDefinition("default.dataSource", beanDefinition);
        }
    }

}
