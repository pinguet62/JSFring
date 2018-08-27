package fr.pinguet62.jsfring.common.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true)
@PropertySource("classpath:/application-common-security.properties")
public class CommonSecurityConfig {
}
