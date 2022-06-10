package com.nhnacademy.minidoorayuserapi.user.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidoorayuserapi.user.dto.UserDetailsDto;
import com.nhnacademy.minidoorayuserapi.user.dto.UserPasswordDto;
import com.nhnacademy.minidoorayuserapi.user.dto.UserPasswordRequest;
import com.nhnacademy.minidoorayuserapi.user.dto.UserSignUpRequest;
import com.nhnacademy.minidoorayuserapi.user.entity.UserStatus;
import com.nhnacademy.minidoorayuserapi.user.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    void existPassword() throws Exception {
        UserPasswordRequest passwordRequest = new UserPasswordRequest();
        passwordRequest.setUserId("id");
        String jsonBody = new ObjectMapper().writeValueAsString(passwordRequest);

        UserPasswordDto password = new UserPasswordDto();
        password.setPassword("password");
        given(userService.findUserPassword("id")).willReturn(password);;

        mockMvc.perform(post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.password", equalTo("password")));

        verify(userService, times(1)).findUserPassword("id");
    }

    @Test
    void testFindUserDetails() throws Exception {
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setUserNo(1L);
        userDetailsDto.setPassword("password");
        userDetailsDto.setEmail("email@email.com");
        userDetailsDto.setId("id");
        userDetailsDto.setStatus(UserStatus.JOINED);
        given(userService.findUserDetailsByUserId("id")).willReturn(userDetailsDto);;

        mockMvc.perform(get("/users/{id}", "id"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.password", equalTo(userDetailsDto.getPassword())))
            .andExpect(jsonPath("$.email", equalTo(userDetailsDto.getEmail())))
            .andExpect(jsonPath("$.id", equalTo(userDetailsDto.getId())))
            .andExpect(jsonPath("$.status", equalTo(userDetailsDto.getStatus().getState())));

        verify(userService, times(1))
            .findUserDetailsByUserId(userDetailsDto.getId());
    }

    @Test
    void signUp() throws Exception {
        UserSignUpRequest requestBody = new UserSignUpRequest();
        requestBody.setId("id");
        requestBody.setPassword("password");
        requestBody.setEmail("email@nhn.com");

        String jsonBody = new ObjectMapper().writeValueAsString(requestBody);

        given(userService.signUp(requestBody)).willReturn(requestBody);

        mockMvc.perform(post("/users/signUp")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userService, times(1)).signUp(requestBody);
    }
}
