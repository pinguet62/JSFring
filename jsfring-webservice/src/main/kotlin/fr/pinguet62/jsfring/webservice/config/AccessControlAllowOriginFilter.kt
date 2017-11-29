package fr.pinguet62.jsfring.webservice.config

import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders.*
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_OK

/**
 * Configure the {@code "Access-Control-Allow-Origin"}.
 */
@Component
@Order(HIGHEST_PRECEDENCE)
class AccessControlAllowOriginFilter : Filter {

    override fun init(filterConfig: FilterConfig) {}

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        var httpRequest: HttpServletRequest = request as HttpServletRequest
        var httpResponse: HttpServletResponse = response as HttpServletResponse

        httpResponse.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        httpResponse.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "Authorization, Origin, X-Requested-With, Content-Type")
        httpResponse.setHeader(ACCESS_CONTROL_ALLOW_METHODS, HttpMethod.values().joinToString(","))
        if (httpRequest.getMethod().equals("OPTIONS"))
            httpResponse.setStatus(SC_OK)
        else
            chain.doFilter(request, response)
    }

    override fun destroy() {}

}