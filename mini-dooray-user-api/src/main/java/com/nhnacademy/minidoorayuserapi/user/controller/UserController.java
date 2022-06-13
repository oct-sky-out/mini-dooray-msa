package com.nhnacademy.minidoorayuserapi.user.controller;

import com.nhnacademy.minidoorayuserapi.user.dto.SocialLoginEmailVerifyDto;
import com.nhnacademy.minidoorayuserapi.user.dto.UserDetailsDto;
import com.nhnacademy.minidoorayuserapi.user.dto.UserPasswordDto;
import com.nhnacademy.minidoorayuserapi.user.dto.UserPasswordRequest;
import com.nhnacademy.minidoorayuserapi.user.dto.UserSignUpRequest;
import com.nhnacademy.minidoorayuserapi.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDetailsDto findUserDetails(@PathVariable("id") String userId){
        return userService.findUserDetailsByUserId(userId);
    }

    @PostMapping
    public UserPasswordDto existPassword(@Validated @RequestBody UserPasswordRequest requestBody){
        return userService.findUserPassword(requestBody.getUserId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signUp")
    public UserSignUpRequest signUp(@Validated @RequestBody UserSignUpRequest signUpRequest){
        return userService.signUp(signUpRequest);
    }

    @PostMapping("/email")
    public UserDetailsDto verifyEmail(@Validated @RequestBody
                                       SocialLoginEmailVerifyDto socialLoginEmailVerify){
        return userService.findByUserDetailsByEmail(socialLoginEmailVerify);
    }
}
