package fr.pinguet62.jsfring.gui.i18n;

import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;

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

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Filter who intercepts GET parameters used to change user's {@link Locale}. <br>
 * {@link LangBean#setLocale(Locale) Set the locale} when the value is present.
 *
 * @see LangBean
 * @see #PARAMETER
 */
@WebFilter("/*")
public final class LangFilter implements Filter {

    /** The parameter name. */
    public static final String PARAMETER = "lang";

    /** @see #init(FilterConfig) */
    private BeanFactory factory;

    /** No action. */
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
        String locale = ((HttpServletRequest) request).getParameter(PARAMETER);
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
    public void init(FilterConfig config) throws ServletException {
        factory = getRequiredWebApplicationContext(config.getServletContext());
    }

}