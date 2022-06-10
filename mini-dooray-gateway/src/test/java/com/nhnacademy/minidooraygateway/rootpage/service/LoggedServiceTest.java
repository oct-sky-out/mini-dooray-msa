package com.nhnacademy.minidooraygateway.rootpage.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mock.web.MockHttpSession;

@SpringBootTest
class LoggedServiceTest {
    @Autowired
    LoggedService loggedService;

    @MockBean
    RedisTemplate<String, String> redisTemplate;

    @Test
    void verifyLoggedFromRedis() {
        Cookie cookie = new Cookie("SESSION", "session_value");
        String sessionId = cookie.getValue();

        HashOperations<String, Object, Object> hashOperationsMock = mock(HashOperations.class);
        given(redisTemplate.opsForHash())
            .willReturn(hashOperationsMock);

        given(hashOperationsMock.get(sessionId, "userId"))
            .willReturn("user1");

        given(hashOperationsMock.get(sessionId, "authority"))
            .willReturn("ROLE_USER");

        HttpServletRequest request = mock(HttpServletRequest.class);
        given(request.getSession(false))
            .willReturn(new MockHttpSession());

        loggedService.verifyLoggedFromRedis(cookie, request);

        verify(redisTemplate, times(2)).opsForHash();
        verify(hashOperationsMock, times(1))
            .get(sessionId, "authority");
        verify(hashOperationsMock, times(1))
            .get(sessionId, "userId");
    }
}
