package fr.pinguet62.jsfring.webservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import static fr.pinguet62.jsfring.webservice.config.Oauth2Helper.HEADER_AUTHORIZATION;

@Configuration
public class RestTemplateConfig {

    public static final String BASE_URL = "http://localhost:8080";

    @Autowired
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