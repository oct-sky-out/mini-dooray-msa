package com.nhnacademy.minidooraygateway.config;

import com.nhnacademy.minidooraygateway.interceptor.LoggedInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final RedisTemplate<String, String> redisTemplate;

    public WebConfig(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggedInterceptor(redisTemplate))
            .addPathPatterns("/project/**")
            .excludePathPatterns("/login/**", "/signIn", "/logout");
    }
}
