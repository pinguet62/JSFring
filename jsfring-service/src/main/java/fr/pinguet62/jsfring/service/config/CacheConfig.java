package fr.pinguet62.jsfring.service.config;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String PROFILE_CACHE = "profile";

    public static final String RIGHT_CACHE = "right";

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache(RIGHT_CACHE), new ConcurrentMapCache(PROFILE_CACHE)));

        return cacheManager;
    }

}