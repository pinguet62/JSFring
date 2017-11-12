package fr.pinguet62.jsfring.webservice.config;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpMethod.values;

/**
 * Configure the {@code "Access-Control-Allow-Origin"}.
 */
@Component
@Order(HIGHEST_PRECEDENCE)
public class AccessControlAllowOriginFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        httpResponse.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "Authorization, Origin, X-Requested-With, Content-Type");
        httpResponse.setHeader(ACCESS_CONTROL_ALLOW_METHODS, stream(values()).map(it -> it.toString()).collect(joining(", ")));
        if (httpRequest.getMethod().equals("OPTIONS"))
            httpResponse.setStatus(SC_OK);
        else
            chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

}