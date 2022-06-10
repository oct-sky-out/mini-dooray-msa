package com.nhnacademy.minidooraygateway.rootpage.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.minidooraygateway.rootpage.service.LoggedService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

@Disabled
@WebMvcTest
class RootPageControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    LoggedService loggedService;

    @Test
    void showRootPage() throws Exception {
        Cookie cookie = new Cookie("SESSION", "session_value");
        HttpServletRequest request = mock(HttpServletRequest.class);

        doNothing().when(loggedService).verifyLoggedFromRedis(cookie, request);

        mockMvc.perform(get("/")
                .cookie()
                .session(new MockHttpSession()))
            .andExpect(status().isOk())
            .andExpect(result -> {
                assertThat(result.getRequest().getSession(false)
                    .getAttribute("userId")).isNotNull();
            })
            .andExpect(view().name("index"));

        verify(loggedService, times(1)).verifyLoggedFromRedis(cookie, request);
    }
}
