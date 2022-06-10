package com.nhnacademy.minidooraygateway.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.minidooraygateway.user.adptor.SignUpAdaptor;
import com.nhnacademy.minidooraygateway.user.dto.SignUpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class SignUpServiceTest {
    @Autowired
    SignUpService signUpService;
    @MockBean
    SignUpAdaptor signUpAdaptor;

    @Test
    void signUp() {
        SignUpRequest requestBody = new SignUpRequest("id", "pw", "email@email.com");

        given(signUpAdaptor.processSignUp(requestBody)).willReturn(requestBody);

        signUpService.signUp(requestBody);

        verify(signUpAdaptor, times(1)).processSignUp(requestBody);
    }
}
