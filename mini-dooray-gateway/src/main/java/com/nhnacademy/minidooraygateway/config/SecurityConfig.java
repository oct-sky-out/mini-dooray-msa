package com.nhnacademy.minidooraygateway.config;

import com.nhnacademy.minidooraygateway.security.handler.LoginSuccessHandler;
import com.nhnacademy.minidooraygateway.security.provider.CustomDaoAuthenticationProvider;
import com.nhnacademy.minidooraygateway.security.provider.CustomSessionAuthenticationStrategy;
import com.nhnacademy.minidooraygateway.security.service.CustomUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().permitAll()
            .and()
            .formLogin()
                .loginPage("/login")
                .usernameParameter("id")
                .passwordParameter("pw")
                .defaultSuccessUrl("/")
                .successHandler(loginSuccessHandler(null))
                .loginProcessingUrl("/login")
            .and()
            .logout()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionFixation().none()
                .sessionAuthenticationStrategy(customSessionAuthenticationStrategy(null))
            .and()
                .headers()
                    .defaultsDisabled()
                        .contentTypeOptions()
                    .and()
                        .cacheControl()
                    .and()
                        .xssProtection().block(true)
                    .and()
                        .frameOptions()
                            .sameOrigin()
            .and()
            .exceptionHandling()
                .accessDeniedPage("/error/403")
            .and();
    }

    public AuthenticationSuccessHandler loginSuccessHandler(@Autowired RedisTemplate<String, String> redisTemplate) {
        return new LoginSuccessHandler(redisTemplate);
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider(null, null));
    }

    public AuthenticationProvider authenticationProvider(@Autowired CustomUserDetailService customUserDetailService,
                                                         @Autowired RestTemplate restTemplate) {
        CustomDaoAuthenticationProvider customDaoAuthenticationProvider =
            new CustomDaoAuthenticationProvider(restTemplate);
        customDaoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        customDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return customDaoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public SessionAuthenticationStrategy customSessionAuthenticationStrategy(@Autowired RedisTemplate<String, String> redisTemplate) {
        return new CustomSessionAuthenticationStrategy(redisTemplate);
    }
}
