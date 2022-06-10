package com.nhnacademy.minidooraygateway.user.service;

import com.nhnacademy.minidooraygateway.user.adptor.SignUpAdaptor;
import com.nhnacademy.minidooraygateway.user.dto.SignUpRequest;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {
    private final SignUpAdaptor signUpAdaptor;

    public SignUpService(SignUpAdaptor signUpAdaptor) {
        this.signUpAdaptor = signUpAdaptor;
    }

    public void signUp(SignUpRequest signUpRequest) {
        signUpAdaptor.processSignUp(signUpRequest);
    }
}
