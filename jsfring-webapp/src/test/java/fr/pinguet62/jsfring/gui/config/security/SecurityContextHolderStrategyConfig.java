package fr.pinguet62.jsfring.gui.config.security;

import static org.springframework.security.core.context.SecurityContextHolder.SYSTEM_PROPERTY;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.context.SecurityContextImpl;

@Configuration
public class SecurityContextHolderStrategyConfig {

    /**
     * Like {@link org.springframework.security.core.context.GlobalSecurityContextHolderStrategy}, but where
     * {@link #createEmptyContext()} return the current static {@link SecurityContext}.
     * <p>
     * This {@link SecurityContextHolderStrategy} is <b>not safe</b>: it must be used only with unit-tests.
     */
    public static final class StaticSecurityContextHolderStrategy implements SecurityContextHolderStrategy {

        private static SecurityContext contextHolder = new SecurityContextImpl();

        @Override
        public void clearContext() {
        }

        @Override
        public SecurityContext createEmptyContext() {
            return contextHolder;
        }

        @Override
        public SecurityContext getContext() {
            return contextHolder;
        }

        @Override
        public void setContext(SecurityContext context) {
            contextHolder = context;
        }

    }

    /** Define the {@link SecurityContextHolderStrategy} to use into {@link SecurityContextHolder}. */
    static {
        System.setProperty(SYSTEM_PROPERTY, StaticSecurityContextHolderStrategy.class.getName());
    }

}