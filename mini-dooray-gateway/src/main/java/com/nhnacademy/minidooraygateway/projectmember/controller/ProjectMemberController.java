package com.nhnacademy.minidooraygateway.projectmember.controller;

import com.nhnacademy.minidooraygateway.user.response.UserFindAllResponse;
import com.nhnacademy.minidooraygateway.user.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/projects/{projectNo}/member")
public class ProjectMemberController {
    private final UserService userService;

    public ProjectMemberController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showProjectMemberManagePage(@PathVariable String projectNo, Model model,
                                              @RequestParam(value = "size", required = false)Integer size,
                                              @RequestParam(value = "page", required = false)Integer page,
                                              HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Long userNo = (Long)session.getAttribute("userNo");
        UserFindAllResponse joinedUsers = userService.findMiniDoorayMembers(userNo, size, page);

        model.addAttribute("projectNo", projectNo);
        model.addAttribute("users", joinedUsers.getUsers());
        model.addAttribute("currentPage", joinedUsers.getCurrentPage());
        model.addAttribute("next", joinedUsers.getHasNext());
        model.addAttribute("previous", joinedUsers.getHasPrevious());

        return "project/member/memberInvite";
    }

}
