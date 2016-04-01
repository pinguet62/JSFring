package fr.pinguet62.jsfring.batch;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:/org/springframework/batch/admin/web/resources/webapp-config.xml",
        "classpath:/org/springframework/batch/admin/web/resources/servlet-config.xml" })
public class BatchAdminConfig {}