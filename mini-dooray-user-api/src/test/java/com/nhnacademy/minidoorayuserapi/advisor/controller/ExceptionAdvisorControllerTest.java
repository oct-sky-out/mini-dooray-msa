package com.nhnacademy.minidoorayuserapi.advisor.controller;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidoorayuserapi.user.controller.UserController;
import com.nhnacademy.minidoorayuserapi.user.dto.UserSignUpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class ExceptionAdvisorControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserController controller;

    @Test
    void handleValidationExceptions() throws Exception {
        UserSignUpRequest signUpRequest = new UserSignUpRequest();
        signUpRequest.setEmail("abc@nhn.com");
        signUpRequest.setPassword("password");
        signUpRequest.setId("");

        String json = new ObjectMapper().writeValueAsString(signUpRequest);

        mockMvc.perform(post("/users/signUp")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isBadRequest());
    }
}
