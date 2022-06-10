package com.nhnacademy.minidooraygateway.security.handler;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final RedisTemplate<String, String> redisTemplate;

    public LoginSuccessHandler(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
        throws ServletException, IOException {
        super.onAuthenticationSuccess(request, response, authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ArrayList<GrantedAuthority> authorities = new ArrayList<>(userDetails.getAuthorities());

        HttpSession session = request.getSession(false);
        session.setAttribute("userId", userDetails.getUsername());

        redisTemplate.opsForHash().put(session.getId(), "userId", userDetails.getUsername());
        redisTemplate.opsForHash().put(session.getId(), "authority", authorities.get(0));
        redisTemplate.boundHashOps(session.getId()).expire(Duration.ofDays(3));
    }
}
