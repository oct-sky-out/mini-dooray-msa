package com.nhnacademy.minidooraygateway.security.provider;

import com.nhnacademy.minidooraygateway.security.dto.UserPasswordDto;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {
    private final RestTemplate restTemplate;

    public CustomDaoAuthenticationProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
        throws AuthenticationException {
        Optional.ofNullable(authentication.getCredentials())
            .ifPresentOrElse(
                credentials -> {
                    String password = credentials.toString();
                    ResponseEntity<UserPasswordDto> userPassword = getPasswordFromUserApiServer(userDetails);

                    if(!Objects.equals(userPassword.getBody(), password)) {
                        throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "로그인정보가 올바르지않음."));
                    }
                },
                () -> {
                    throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "로그인정보가 올바르지않음."));
                }
            );
    }

    private ResponseEntity<UserPasswordDto> getPasswordFromUserApiServer(UserDetails userDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(
            "http://localhost:8082/{userId}/password",
            HttpMethod.GET,
            httpEntity,
            UserPasswordDto.class,
            userDetails.getUsername());
    }
}
