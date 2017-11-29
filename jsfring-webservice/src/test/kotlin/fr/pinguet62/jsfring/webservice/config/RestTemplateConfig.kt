package fr.pinguet62.jsfring.webservice.config

import fr.pinguet62.jsfring.webservice.config.Oauth2Helper.Companion.HEADER_AUTHORIZATION
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.web.client.RestTemplate

@Configuration
open class RestTemplateConfig {

    companion object {
        const val BASE_URL = "http://localhost:8080"
    }

    @Autowired
    lateinit var helper: Oauth2Helper

    @Bean
    open fun authenticatedRestTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.rootUri(BASE_URL).interceptors(oauth2Interceptor2()).build()
    }

    private fun oauth2Interceptor2() = ClientHttpRequestInterceptor { request, body, execution ->
        request.headers.add(HEADER_AUTHORIZATION, helper.getAuthorization())
        execution.execute(request, body)
    }

}
