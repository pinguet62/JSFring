package fr.pinguet62.jsfring.webservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import static java.util.Objects.requireNonNull;

@Configuration
public class FixOAuth2AutoConfiguration {

    @Configuration
    public static class FixAuthorizationServerEndpointsConfiguration extends AuthorizationServerConfigurerAdapter {
        private final BaseClientDetails details;

        @Autowired
        private AuthenticationManager authenticationManager;

        public FixAuthorizationServerEndpointsConfiguration(BaseClientDetails details) {
            this.details = requireNonNull(details);
        }

        /**
         * Fix (temporary?) removed OAuth2 auto-configuration.
         */
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient(details.getClientId())
                    .secret(details.getClientSecret())
                    .authorizedGrantTypes(details.getAuthorizedGrantTypes().toArray(new String[0]))
                    .authorities(details.getAuthorities().toArray(new String[0]))
                    .scopes(details.getScope().toArray(new String[0]));
        }

        /**
         * Support for {@code password} grand-type, enabled only if {@link AuthenticationManager} is set.
         * <p>
         * Fix (temporary?) removed OAuth2 auto-configuration.
         */
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authenticationManager(authenticationManager);
        }
    }

    /**
     * Fix (temporary?) removed OAuth2 auto-configuration.
     */
    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    public BaseClientDetails oauth2ClientDetails() {
        return new BaseClientDetails();
    }

}
