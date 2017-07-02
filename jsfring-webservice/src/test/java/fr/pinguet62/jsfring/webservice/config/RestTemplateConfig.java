package fr.pinguet62.jsfring.webservice.config;

import static fr.pinguet62.jsfring.webservice.config.Oauth2Helper.HEADER_AUTHORIZATION;

import javax.inject.Inject;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    public static final String BASE_URL = "http://localhost:8080";

    @Inject
    private Oauth2Helper helper;

    @Bean
    public RestTemplate authenticatedRestTemplate(RestTemplateBuilder builder) {
        return builder.rootUri(BASE_URL).interceptors(oauth2Interceptor()).build();
    }

    private ClientHttpRequestInterceptor oauth2Interceptor() {
        return (request, body, execution) -> {
            request.getHeaders().add(HEADER_AUTHORIZATION, helper.getAuthorization());
            return execution.execute(request, body);
        };
    }

}