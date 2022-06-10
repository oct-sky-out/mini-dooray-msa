package com.nhnacademy.minidooraygateway.security.provider;

import com.nhnacademy.minidooraygateway.security.dto.UserPasswordDto;
import com.nhnacademy.minidooraygateway.security.dto.UserPasswordRequest;
import java.util.Optional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {
    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;

    public CustomDaoAuthenticationProvider(RestTemplate restTemplate,
                                           PasswordEncoder passwordEncoder) {
        this.restTemplate = restTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
        throws AuthenticationException {
        Optional.ofNullable(authentication.getCredentials())
            .ifPresentOrElse(
                credentials -> {
                    String password = credentials.toString();
                    UserPasswordDto userPassword = getPasswordFromUserApiServer(userDetails);

                    if(passwordEncoder.matches(password, userPassword.getPassword())) {
                        throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "로그인정보가 올바르지않음."));
                    }
                },
                () -> {
                    throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "로그인정보가 올바르지않음."));
                }
            );
    }

    private UserPasswordDto getPasswordFromUserApiServer(UserDetails userDetails) {
        UserPasswordRequest requestBody = new UserPasswordRequest();
        requestBody.setUserId(userDetails.getUsername());

        return restTemplate.postForEntity(
            "http://localhost:8082/users",
            requestBody,
            UserPasswordDto.class)
            .getBody();
    }
}
