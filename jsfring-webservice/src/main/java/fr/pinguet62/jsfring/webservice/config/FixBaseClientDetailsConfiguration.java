package fr.pinguet62.jsfring.webservice.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

/** @see <a href="https://github.com/spring-projects/spring-boot/issues/9290">GitHub spring-boot #9290</a> */
@Configuration
public class FixBaseClientDetailsConfiguration {

    @Configuration
    @ConditionalOnMissingBean(BaseClientDetails.class)
    protected static class BaseClientDetailsConfiguration {

        private final OAuth2ClientProperties client;

        protected BaseClientDetailsConfiguration(OAuth2ClientProperties client) {
            this.client = client;
        }

        @Bean
        @ConfigurationProperties(prefix = "security.oauth2.client")
        public BaseClientDetails oauth2ClientDetails() {
            BaseClientDetails details = new BaseClientDetails();
            if (this.client.getClientId() == null) {
                this.client.setClientId(UUID.randomUUID().toString());
            }
            details.setClientId(this.client.getClientId());
            details.setClientSecret(this.client.getClientSecret());
            details.setAuthorizedGrantTypes(
                    Arrays.asList("authorization_code", "password", "client_credentials", "implicit", "refresh_token"));
            details.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
            details.setRegisteredRedirectUri(Collections.<String> emptySet());

            // Fix
            details.setScope(new HashSet<>(details.getScope()));

            return details;
        }

    }

}