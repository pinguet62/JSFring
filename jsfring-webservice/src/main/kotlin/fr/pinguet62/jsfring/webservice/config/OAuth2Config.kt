package fr.pinguet62.jsfring.webservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer

@Configuration
@EnableAuthorizationServer
@EnableResourceServer
@Order(-20) // before basic authentication filter
open class OAuth2Config {

    @Configuration
    open class AuthenticationManagerConfiguration(
            val userDetailsService: UserDetailsService
    ) : WebSecurityConfigurerAdapter() {
        /**
         * Use custom {@link UserDetailsService}.
         */
        override fun configure(auth: AuthenticationManagerBuilder) {
            auth.userDetailsService(userDetailsService)
        }

        /**
         * Fix (temporary?) removed OAuth2 auto-configuration.
         */
        @Bean
        override fun authenticationManager(): AuthenticationManager = super.authenticationManager()
    }

}