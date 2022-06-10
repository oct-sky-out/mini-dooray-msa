package com.nhnacademy.minidooraygateway.config;

import com.nhnacademy.minidooraygateway.security.handler.LoginSuccessHandler;
import com.nhnacademy.minidooraygateway.security.provider.CustomDaoAuthenticationProvider;
import com.nhnacademy.minidooraygateway.security.provider.CustomSessionAuthenticationStrategy;
import com.nhnacademy.minidooraygateway.security.service.CustomUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
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
            .and();

        http.sessionManagement(httpSecuritySessionManagementConfigurer ->
                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                    SessionCreationPolicy.ALWAYS))
            .sessionManagement()
                .sessionFixation().none()
                .sessionAuthenticationStrategy(customSessionAuthenticationStrategy(null))
            .and();

        http.oauth2Login()
                .defaultSuccessUrl("/")
                .successHandler(loginSuccessHandler(null))
            .and();

        http.formLogin()
                .loginPage("/signIn")
                .usernameParameter("id")
                .passwordParameter("pw")
                .defaultSuccessUrl("/")
                .successHandler(loginSuccessHandler(null))
                .loginProcessingUrl("/login")
            .and();

        http.logout()
            .clearAuthentication(true)
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .and();

        http.csrf()
            .disable();

        http.headers()
                .defaultsDisabled()
                .contentTypeOptions()
            .and()
                .cacheControl()
            .and()
                .xssProtection().block(true)
            .and()
                .frameOptions().sameOrigin()
            .and()
                .exceptionHandling().accessDeniedPage("/error/403")
            .and();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler(
        RedisTemplate<String, String> redisTemplate) {
        return new LoginSuccessHandler(redisTemplate);
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider(null, null));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
        CustomUserDetailService customUserDetailService,
        RestTemplate restTemplate) {
        DaoAuthenticationProvider customDaoAuthenticationProvider =
            new CustomDaoAuthenticationProvider(restTemplate, passwordEncoder());
        customDaoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        customDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return customDaoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionAuthenticationStrategy customSessionAuthenticationStrategy(
        RedisTemplate<String, String> redisTemplate) {
        return new CustomSessionAuthenticationStrategy(redisTemplate);
    }

    @Bean
    public ClientRegistration githubLogin() {
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
            .userNameAttributeName("email")
            .clientId("39249c7a9019cd6ab51a")
            .redirectUri("{baseUrl}/login/oauth2/code/github")
            .clientSecret("993f21e34413ebf5aa25d26fb79da2299f2b6009")
            .scope("user")
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .build();
    }
}
