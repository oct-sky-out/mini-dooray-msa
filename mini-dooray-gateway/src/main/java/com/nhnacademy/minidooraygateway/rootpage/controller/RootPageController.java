package com.nhnacademy.minidooraygateway.rootpage.controller;

import com.nhnacademy.minidooraygateway.rootpage.service.LoggedService;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class RootPageController {
    private final LoggedService loggedService;

    public RootPageController(LoggedService loggedService) {
        this.loggedService = loggedService;
    }

    @GetMapping
    public String showRootPage(HttpServletRequest request) {
        return "index";
    }
}
