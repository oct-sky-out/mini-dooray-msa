package com.nhnacademy.minidoorayuserapi.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.minidoorayuserapi.user.dto.UserSignUpRequest;
import com.nhnacademy.minidoorayuserapi.user.entity.User;
import com.nhnacademy.minidoorayuserapi.user.entity.UserStatus;
import com.nhnacademy.minidoorayuserapi.user.repository.UserRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    void findUserPassword() {
    }

    @Test
    void findUserDetailsByUserId() {
    }

    @Test
    void signUp() {
        String id = "id";
        String pw = "pw123";
        String email = "id@abc.com";

        UserSignUpRequest body = new UserSignUpRequest();
        body.setId(id);
        body.setPassword(pw);
        body.setEmail(email);

        UserSignUpRequest saved = userService.signUp(body);
        assertThat(saved).isEqualTo(body);

    }
}
