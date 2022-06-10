package com.nhnacademy.minidooraygateway.user.controller;

import com.nhnacademy.minidooraygateway.user.service.SignUpService;
import com.nhnacademy.minidooraygateway.user.dto.SignUpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signUp")
public class SignUpController {
    private final SignUpService signUpService;

    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @GetMapping
    public String showSignUpPage(){
        return "user/signUp";
    }

    @PostMapping
    public String signUpProcess(@ModelAttribute SignUpRequest signUpRequest){
        signUpService.signUp(signUpRequest);
        
        return "redirect:/login";
    }
}
