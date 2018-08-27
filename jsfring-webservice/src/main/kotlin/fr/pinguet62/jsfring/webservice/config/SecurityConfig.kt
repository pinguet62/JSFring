package fr.pinguet62.jsfring.webservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint
import org.springframework.security.oauth2.provider.endpoint.RedirectResolver
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint

@Configuration
open class SecurityConfig {

    @Configuration
    @EnableWebSecurity
    open class SecurityConfigurer : WebSecurityConfigurerAdapter() {
        /**
         * **Restore old behavior** `SpringBootWebSecurityConfiguration.ApplicationWebSecurityConfigurerAdapter`
         * <br>
         * Add *native dialog form* to `/oauth/authorize` when header `Authorization` not set.
         * <br>
         * Set [BasicAuthenticationEntryPoint] to default configuration.
         */
        override fun configure(http: HttpSecurity) {
            super.configure(http)

            val entryPoint = BasicAuthenticationEntryPoint()
            entryPoint.realmName = "Spring"
            http
                    .exceptionHandling().authenticationEntryPoint(entryPoint).and()
                    .httpBasic().authenticationEntryPoint(entryPoint)
        }
    }

    @Configuration
    @EnableAuthorizationServer
    @EnableResourceServer
    open class OAuth2Config {

        /**
         * **Custom behavior**
         * <br>
         * Disable `security.oauth2.client.registered-redirect-uri`
         * for easy deploy and configuration.
         */
        @Configuration
        open class DisableRedirectResolverConfig(authorizationEndpoint: AuthorizationEndpoint) {
            init {
                val noRedirectResolver = RedirectResolver { requestedRedirect, _ -> requestedRedirect }
                authorizationEndpoint.setRedirectResolver(noRedirectResolver)
            }
        }

    }

}
