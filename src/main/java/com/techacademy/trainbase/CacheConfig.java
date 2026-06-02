package com.techacademy.trainbase;


import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    // Spring Boot auto-configuration automatically detects ehcache.xml
    // and natively configures the JCacheCacheManager for us.

}