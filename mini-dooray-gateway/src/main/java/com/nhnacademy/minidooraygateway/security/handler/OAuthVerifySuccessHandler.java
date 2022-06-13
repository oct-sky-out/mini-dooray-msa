package com.nhnacademy.minidooraygateway.security.handler;

import com.nhnacademy.minidooraygateway.exceptions.HasNotUserEmailException;
import com.nhnacademy.minidooraygateway.security.dto.CustomUserDerailsResponse;
import com.nhnacademy.minidooraygateway.security.dto.SocialLoginEmailVerifyDto;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class OAuthVerifySuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final RedisTemplate<String ,String> redisTemplate;
    private final RestTemplate restTemplate;

    public OAuthVerifySuccessHandler(RedisTemplate<String, String> redisTemplate,
                                      RestTemplate restTemplate) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
        throws ServletException, IOException {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;

        CustomUserDerailsResponse userDetails =
            verifyEmailToUserApiServer(authenticationToken, request, response);
        if(userDetails.getStatus().equals("LEAVED")){
            return;
        }

        ArrayList<GrantedAuthority> authorities = new ArrayList<>(userDetails.getAuthorities());

        HttpSession session = request.getSession(false);
        session.setAttribute("userId", userDetails.getUsername());
        session.setAttribute("userNo", userDetails.getUserNo());

        Cookie cookie = new Cookie("SESSION", session.getId());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(259200);
        cookie.setDomain("/");
        response.addCookie(cookie);

        redisTemplate.opsForHash().put(session.getId(), "userId", userDetails.getUsername());
        redisTemplate.opsForHash().put(session.getId(), "userNo", String.valueOf(userDetails.getUserNo()));
        redisTemplate.opsForHash().put(session.getId(), "authority", authorities.get(0).getAuthority());
        redisTemplate.boundHashOps(session.getId()).expire(Duration.ofDays(3));
    }

    private CustomUserDerailsResponse verifyEmailToUserApiServer(OAuth2AuthenticationToken authenticationToken,
                                                                 HttpServletRequest request,
                                                                 HttpServletResponse response)
        throws IOException {
        Object email = Optional.ofNullable(authenticationToken.getPrincipal().getAttribute("email"))
            .orElseThrow(HasNotUserEmailException::new);
        SocialLoginEmailVerifyDto socialLoginEmailVerifyDto = new SocialLoginEmailVerifyDto();
        socialLoginEmailVerifyDto.setEmail(email.toString());
        try {
            return restTemplate.postForEntity(
                "http://localhost:8082/users/email",
                socialLoginEmailVerifyDto,
                CustomUserDerailsResponse.class
            ).getBody();
        } catch (HttpClientErrorException e) {
            CustomUserDerailsResponse failCase = new CustomUserDerailsResponse();
            failCase.setStatus("LEAVED");
            failCase.setEmail("none");
            failCase.setUserNo(0L);
            failCase.setPassword("none");
            failCase.setId("none");

            return failCase;
        }
    }


}
