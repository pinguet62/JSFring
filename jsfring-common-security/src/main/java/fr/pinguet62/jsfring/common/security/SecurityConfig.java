package fr.pinguet62.jsfring.common.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {
}