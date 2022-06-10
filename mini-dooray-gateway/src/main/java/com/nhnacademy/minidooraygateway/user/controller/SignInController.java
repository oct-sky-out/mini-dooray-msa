package com.nhnacademy.minidooraygateway.user.controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signIn")
public class SignInController {
    @GetMapping
    public String showSignInPage(){
        return "user/signIn";
    }
}
