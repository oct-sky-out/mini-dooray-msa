package com.nhnacademy.minidooraygateway.user.controller;

import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.minidooraygateway.user.dto.SignUpRequest;
import com.nhnacademy.minidooraygateway.user.service.SignUpService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@Disabled
@WebMvcTest(SignUpController.class)
class SignUpControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SignUpService signUpService;

    @Test
    void showSignUpPage() throws Exception {
        mockMvc.perform(get("/signUp"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/signUp"));
    }

    @Test
    void signUpProcess() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("id", "password", "email@Nhn.com");
        willDoNothing().given(signUpService).signUp(signUpRequest);

        Map<String, Object> attribute = new HashMap();
        attribute.put("SignUpRequest", signUpRequest);
        mockMvc.perform(post("/signUp")
                .flashAttrs(attribute))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/login"));
    }
}
