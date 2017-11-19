package fr.pinguet62.jsfring.batch.admin.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Declarated as <b>auto-configuration</b> in order to be executed after {@link DataSourceAutoConfiguration}.<br>
 * See {@link BatchAdminAutoConfigurationFix}.
 * <p>
 * <i>Duplicated</i> configuration files, in order to disable {@code &lt;mvc:annotation-driven /&gt;}.
 */
@Configuration
@ImportResource({
        "classpath:/webapp-config.xml", // fix: "classpath:/org/springframework/batch/admin/web/resources/webapp-config.xml"
        "classpath:/servlet-config.xml" // fix: "classpath:/org/springframework/batch/admin/web/resources/servlet-config.xml"
})
public class BatchAdminAutoConfiguration {
}
