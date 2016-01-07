package fr.pinguet62.jsfring.ws.config;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static javax.ws.rs.Priorities.HEADER_DECORATOR;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static org.springframework.http.HttpMethod.values;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

/** Configure the {@code Access-Control-Allow-Origin}. */
@Provider
@Priority(HEADER_DECORATOR)
public class AccessControlAllowOriginFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
        MultivaluedMap<String, Object> headers = response.getHeaders();
        headers.add(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        headers.add(ACCESS_CONTROL_ALLOW_HEADERS, "Authorization, Origin, X-Requested-With, Content-Type");
        // headers.add("Access-Control-Expose-Headers","Location,
        // Content-Disposition");
        // headers.add("Access-Control-Allow-Credentials", true);
        headers.add(ACCESS_CONTROL_ALLOW_METHODS, stream(values()).map(it -> it.toString()).collect(joining(", "))); // TODO
                                                                                                                     // Check
                                                                                                                     // "*"
    }

}