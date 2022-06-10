package com.nhnacademy.minidooraygateway.user.adptor;

import com.nhnacademy.minidooraygateway.user.dto.SignUpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SignUpAdaptor {
    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;

    public SignUpAdaptor(RestTemplate restTemplate, PasswordEncoder passwordEncoder) {
        this.restTemplate = restTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    public SignUpRequest processSignUp(SignUpRequest signUpRequest) {
        String hashedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        signUpRequest.changeHashedPassword(hashedPassword);

        return restTemplate.postForEntity(
            "http://localhost:8082/users/signUp",
            signUpRequest,
            SignUpRequest.class
        ).getBody();
    }
}
