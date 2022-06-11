package com.nhnacademy.minidoorayuserapi.user.service;

import com.nhnacademy.minidoorayuserapi.user.dto.UserDetailsDto;
import com.nhnacademy.minidoorayuserapi.user.dto.UserPasswordDto;
import com.nhnacademy.minidoorayuserapi.user.dto.UserSignUpRequest;
import com.nhnacademy.minidoorayuserapi.user.entity.User;
import com.nhnacademy.minidoorayuserapi.exceptions.UserNotFoundException;
import com.nhnacademy.minidoorayuserapi.user.entity.UserStatus;
import com.nhnacademy.minidoorayuserapi.user.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserPasswordDto findUserPassword (String userId){
        return userRepository.findPasswordByUserId(userId)
            .orElse(null);
    }

    public UserDetailsDto findUserDetailsByUserId (String userId){
        return userRepository.findUserDetailsById(userId)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보를 찾지 못하였습니다."));
    }

    @Transactional
        public UserSignUpRequest signUp(UserSignUpRequest signUpRequest) {
            User user = User.builder()
                .id(signUpRequest.getId())
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
            .createdAt(LocalDateTime.now())
            .status(UserStatus.JOINED)
            .build();

        userRepository.save(user);
        return signUpRequest;
    }
}
