package com.suraj.rate_limiter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class RedisConfig {

    @Bean
    public RedisScript<Long> slidingWindowScript() {
        return RedisScript.of(
                new ClassPathResource("scripts/sliding-window.lua"),
                Long.class);
    }
}
