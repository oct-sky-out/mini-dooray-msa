package com.nhnacademy.minidoorayuserapi.user.service;

import com.nhnacademy.minidoorayuserapi.user.dto.UserPasswordDto;
import com.nhnacademy.minidoorayuserapi.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserPasswordDto findUserPassword (String userId){
        return userRepository.findPasswordByUserId(userId);
    }
}
