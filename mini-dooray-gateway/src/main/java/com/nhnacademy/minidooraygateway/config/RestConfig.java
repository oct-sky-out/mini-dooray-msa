package com.nhnacademy.minidooraygateway.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@EnableSpringDataWebSupport
@Configuration
public class RestConfig extends WebMvcConfigurationSupport {
    @Bean
    public RestTemplate restTemplate(){
        HttpComponentsClientHttpRequestFactory requestFactory =
            new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        requestFactory.setConnectionRequestTimeout(3000);
        requestFactory.setReadTimeout(3000);

        return new RestTemplate(requestFactory);
    }
}
