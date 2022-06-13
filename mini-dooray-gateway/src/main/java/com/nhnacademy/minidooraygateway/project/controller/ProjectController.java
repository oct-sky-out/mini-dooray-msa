package com.nhnacademy.minidooraygateway.project.controller;

import com.nhnacademy.minidooraygateway.project.request.MyProjectsPageRequest;
import com.nhnacademy.minidooraygateway.project.request.ProjectRegisterRequest;
import com.nhnacademy.minidooraygateway.project.service.ProjectService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String showMyProjects(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        Long userNo = (Long) session.getAttribute("userNo");
        MyProjectsPageRequest myProjectsPage = projectService.findMyProjects(userNo);

        model.addAttribute("projects", myProjectsPage.getProjects());
        model.addAttribute("currentPage", myProjectsPage.getCurrentPage());
        model.addAttribute("hasNext", myProjectsPage.isHasNext());
        model.addAttribute("hasPrevious", myProjectsPage.isHasPrevious());

        return "project/myProjects";
    }

    @GetMapping("/register")
    public String showRegisterProjectPage(){
        return "project/register";
    }

    @PostMapping("/register")
    public String registerProject(ProjectRegisterRequest registerRequest){
        projectService.createProject(registerRequest);
        return "redirect:/projects";
    }

    @PostMapping("/modify")
    public String showModifyProjectPage(){
        return "project/modify";
    }
}
