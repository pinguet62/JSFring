package fr.pinguet62.jsfring.service.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CacheMockConfig {

    @Bean
    @Primary // Mock
    public CacheManager cacheManagerMock() {
        return new NoOpCacheManager();
    }

}