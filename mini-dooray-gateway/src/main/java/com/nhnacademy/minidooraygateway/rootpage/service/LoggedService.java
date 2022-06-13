package com.nhnacademy.minidooraygateway.rootpage.service;

import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class LoggedService {
    private final RedisTemplate<String, String> redisTemplate;

    public LoggedService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void verifyLoggedFromRedis(Cookie cookie, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String sessionId = cookie.getValue();
        String userId = (String) redisTemplate.opsForHash().get(sessionId, "userId");
        String userNo = (String) redisTemplate.opsForHash().get(sessionId, "userNo");
        String authority = (String) redisTemplate.opsForHash().get(sessionId, "authority");
        if(hasUserId(userId)){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);
            User user = new User(
                userId,
                UUID.randomUUID().toString(),
                Collections.singleton(grantedAuthority)
            );
            Authentication authentication =
                new UsernamePasswordAuthenticationToken(user, user.getPassword());
            SecurityContextHolder.getContext()
                .setAuthentication(authentication);

            session.setAttribute("userId", userId);
            session.setAttribute("userNo", userNo);
            session.setAttribute("authority", authority);
        }
    }

    private boolean hasUserId(String userId) {
        return Objects.nonNull(userId) || !userId.isEmpty();
    }

}
