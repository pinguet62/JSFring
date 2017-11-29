package fr.pinguet62.jsfring.webservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.client.BaseClientDetails

@Configuration
open class FixOAuth2AutoConfiguration {

    @Configuration
    open class FixAuthorizationServerEndpointsConfiguration(
            val details: BaseClientDetails,
            var authenticationManager: AuthenticationManager
    ) : AuthorizationServerConfigurerAdapter() {
        /**
         * Fix (temporary?) removed OAuth2 auto-configuration.
         */
        override fun configure(clients: ClientDetailsServiceConfigurer) {
            clients.inMemory()
                    .withClient(details.clientId)
                    .secret(details.clientSecret)
                    .authorizedGrantTypes(*details.authorizedGrantTypes.toTypedArray())
                    .authorities(*details.authorities.map { it.toString() }.toTypedArray())
                    .scopes(*details.scope.toTypedArray())
        }

        /**
         * Support for {@code password} grand-type, enabled only if {@link AuthenticationManager} is set.
         * <p>
         * Fix (temporary?) removed OAuth2 auto-configuration.
         */
        override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
            endpoints.authenticationManager(authenticationManager)
        }
    }

    /**
     * Fix (temporary?) removed OAuth2 auto-configuration.
     */
    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    open fun oauth2ClientDetails(): BaseClientDetails = BaseClientDetails()

}
