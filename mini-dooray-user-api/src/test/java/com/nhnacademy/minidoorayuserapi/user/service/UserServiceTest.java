package com.nhnacademy.minidoorayuserapi.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.minidoorayuserapi.user.dto.SocialLoginEmailVerifyDto;
import com.nhnacademy.minidoorayuserapi.user.dto.UserBasicDto;
import com.nhnacademy.minidoorayuserapi.user.dto.UserDetailsDto;
import com.nhnacademy.minidoorayuserapi.user.dto.UserPasswordDto;
import com.nhnacademy.minidoorayuserapi.user.dto.UserSignUpRequest;
import com.nhnacademy.minidoorayuserapi.user.entity.User;
import com.nhnacademy.minidoorayuserapi.user.entity.UserStatus;
import com.nhnacademy.minidoorayuserapi.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        UserPasswordDto passwordDto = new UserPasswordDto();
        passwordDto.setPassword("password");
        given(userRepository.findPasswordByUserId("id"))
            .willReturn(Optional.of(passwordDto));

        assertThat(userService.findUserPassword("id").getPassword())
            .isEqualTo(passwordDto.getPassword());
    }

    @Test
    void findUserDetailsByUserId() {
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setUserNo(1L);
        userDetailsDto.setId("id");
        userDetailsDto.setPassword("password");
        userDetailsDto.setEmail("email@email.nhn");
        userDetailsDto.setStatus(UserStatus.DORMANT);

        given(userRepository.findUserDetailsById("id"))
            .willReturn(Optional.of(userDetailsDto));

        UserDetailsDto searched = userService.findUserDetailsByUserId("id");
        assertThat(searched.getId()).isEqualTo("id");
        assertThat(searched.getPassword()).isEqualTo("password");
        assertThat(searched.getStatus()).isEqualTo(UserStatus.DORMANT);
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

    @Test
    void findUserDetailsByUserEmailTest() {
        String email = "user1@nhnacademy.com";
        SocialLoginEmailVerifyDto emailVerifyDto = new SocialLoginEmailVerifyDto();
        emailVerifyDto.setEmail(email);

        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setUserNo(1L);
        userDetailsDto.setId("id");
        userDetailsDto.setPassword("password");
        userDetailsDto.setEmail("email@email.nhn");
        userDetailsDto.setStatus(UserStatus.DORMANT);

        given(userRepository.findUserDetailByUserEmail(emailVerifyDto.getEmail()))
            .willReturn(Optional.of(userDetailsDto));

        UserDetailsDto result = userService.findByUserDetailsByEmail(emailVerifyDto);

        assertThat(result).isEqualTo(userDetailsDto);
    }

    @Test
    void findJoinedAllUserTest() {
        Pageable pageable = PageRequest.of(0, 5);
        given(userRepository.findJoinedAllUserByPage(100L, pageable))
            .willReturn(new PageImpl<>(new ArrayList<>(),pageable,0));

        Page<UserBasicDto> joinedUser = userRepository.findJoinedAllUserByPage(100L, pageable);
        assertThat(joinedUser.hasContent()).isFalse();
        assertThat(joinedUser.hasNext()).isFalse();
        assertThat(joinedUser.hasPrevious()).isFalse();
    }
}
