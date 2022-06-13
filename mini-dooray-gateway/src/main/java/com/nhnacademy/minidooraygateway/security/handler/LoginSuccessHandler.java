package com.nhnacademy.minidooraygateway.security.handler;

import com.nhnacademy.minidooraygateway.security.dto.CustomUserDerailsResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

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

        CustomUserDerailsResponse userDetails = (CustomUserDerailsResponse) authentication.getPrincipal();
        ArrayList<GrantedAuthority> authorities = new ArrayList<>(userDetails.getAuthorities());

        HttpSession session = request.getSession(false);
        session.setAttribute("userId", userDetails.getUsername());

        Cookie cookie = new Cookie("SESSION", session.getId());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(259200);
        cookie.setDomain("/");
        response.addCookie(cookie);

        redisTemplate.opsForHash().put(session.getId(), "userId", userDetails.getUsername());
        redisTemplate.opsForHash().put(session.getId(), "userNo", userDetails.getUserNo().toString());
        redisTemplate.opsForHash().put(session.getId(), "authority", authorities.get(0).getAuthority());
        redisTemplate.boundHashOps(session.getId()).expire(Duration.ofDays(3));

        request.setAttribute("isOauth", false);
    }
}
