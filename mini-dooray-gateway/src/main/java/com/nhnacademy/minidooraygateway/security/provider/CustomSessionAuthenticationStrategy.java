package com.nhnacademy.minidooraygateway.security.provider;

import com.nhnacademy.minidooraygateway.exceptions.RedirectionFailureException;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

@Slf4j
public class CustomSessionAuthenticationStrategy implements SessionAuthenticationStrategy {
    private final RedisTemplate<String, String> redisTemplate;

    public CustomSessionAuthenticationStrategy(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request,
                                 HttpServletResponse response)
        throws SessionAuthenticationException {
        Optional<HttpSession> sessionOptional = Optional.ofNullable(request.getSession(false));
        sessionOptional.ifPresentOrElse(httpSession -> checkSessionFromRedis(response, httpSession),
            () -> redirectToLoginPage(response));
    }

    private void checkSessionFromRedis(HttpServletResponse response, HttpSession httpSession) {
        String sessionId = httpSession.getId();
        boolean hasSession  = redisTemplate.opsForHash().hasKey(sessionId, "userId");
        if(!hasSession){
            redirectToLoginPage(response);
        }
    }

    private void redirectToLoginPage(HttpServletResponse response) {
        try {
            response.sendRedirect("/login");
        } catch (IOException e) {
            log.error("{}", e);
            throw new RedirectionFailureException("redirectToLoginPage 에서 리다이렉션 도중 실패하였습니다.");
        }
    }
}
