package com.modular.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    private final Logger log = LoggerFactory.getLogger(CacheConfig.class);

    private CacheManager cacheManager;

    @Bean
    public CacheManager cacheManager() {
        log.warn("CacheManager is NoOpCacheManager");
        cacheManager = new NoOpCacheManager();
        return cacheManager;
    }

}
