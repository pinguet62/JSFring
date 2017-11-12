package fr.pinguet62.jsfring.webapp.jsf.i18n;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;

/**
 * Filter who intercepts GET parameters used to change user's {@link Locale}. <br>
 * {@link LangBean#setLocale(Locale) Set the locale} when the value is present.
 *
 * @see LangBean
 * @see #PARAMETER
 */
@WebFilter("/*")
public final class LangFilter implements Filter {

    /**
     * The parameter name.
     */
    public static final String PARAMETER = "lang";

    /**
     * @see #init(FilterConfig)
     */
    private BeanFactory factory;

    /**
     * No action.
     */
    @Override
    public void destroy() {
        // No action
    }

    /**
     * Get the {@link #PARAMETER}.<br>
     * If it's present: update the {@link LangBean}.
     *
     * @see LangBean#setLocale(Locale)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String locale = request.getParameter(PARAMETER);
        if (locale != null) {
            LangBean langBean = factory.getBean(LangBean.class);
            langBean.setLocale(new Locale(locale));
        }

        chain.doFilter(request, response);
    }

    /**
     * Initialize the {@link #factory}.<br>
     * Filter cannot be injected.
     *
     * @see WebApplicationContextUtils#getRequiredWebApplicationContext(javax.servlet.ServletContext)
     */
    @Override
    public void init(FilterConfig config) {
        factory = getRequiredWebApplicationContext(config.getServletContext());
    }

}