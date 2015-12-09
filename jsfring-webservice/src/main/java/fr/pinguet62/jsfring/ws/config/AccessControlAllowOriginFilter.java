package fr.pinguet62.jsfring.ws.config;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

/** Configure the {@code Access-Control-Allow-Origin}. */
@Provider
@Priority(Priorities.HEADER_DECORATOR)
public class AccessControlAllowOriginFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
        MultivaluedMap<String, Object> headers = response.getHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Headers", "Authorization, Origin, X-Requested-With, Content-Type");
        // headers.add("Access-Control-Expose-Headers",
        // "Location, Content-Disposition");
        // headers.add("Access-Control-Allow-Credentials", true);
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD, OPTIONS");
    }

}