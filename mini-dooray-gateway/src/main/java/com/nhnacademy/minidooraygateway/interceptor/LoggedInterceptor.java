package com.nhnacademy.minidooraygateway.interceptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoggedInterceptor implements HandlerInterceptor {
    private final RedisTemplate<String, String> redisTemplate;

    public LoggedInterceptor(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        Cookie sessionCookie = Arrays.stream(request.getCookies()).reduce((cookie, cookie2) -> {
                if (cookie.getName().equals("SESSION")) {
                    return cookie;
                }
                return cookie2;
            })
            .orElse(null);
        if (Objects.nonNull(sessionCookie)) {
            approveAuthority(request, sessionCookie);
        }

        return true;
    }

    private void approveAuthority(HttpServletRequest request, Cookie sessionCookie) {
        HttpSession session = request.getSession(false);
        Long userNo = (Long) redisTemplate.opsForHash().get(sessionCookie.getValue(), "userNo");
        String userId =
            (String) redisTemplate.opsForHash().get(sessionCookie.getValue(), "userId");
        String authority =
            (String) redisTemplate.opsForHash().get(sessionCookie.getValue(), "authority");

        if (Objects.nonNull(userNo)) {
            session.setAttribute("userNo", userNo);
            session.setAttribute("userId", userId);
            session.setAttribute("authority", authority);

            putAuthorityIntoSecurityContext(session, userNo, userId, authority);
        }
    }

    private void putAuthorityIntoSecurityContext(HttpSession session, Long userNo, String userId, String authority) {
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
    }
}
