package fr.pinguet62.jsfring.gui.i18n;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/** @see LangBean */
@WebFilter("/*")
public final class LangFilter implements Filter {

    private WebApplicationContext context;

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String locale = ((HttpServletRequest) request).getParameter(LangBean.PARAMETER);
        if (locale != null) {
            LangBean langBean = context.getBean(LangBean.class);
            langBean.setLocale(new Locale(locale));
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
    }

}