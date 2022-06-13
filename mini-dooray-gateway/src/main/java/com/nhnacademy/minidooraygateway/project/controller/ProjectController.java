package com.nhnacademy.minidooraygateway.project.controller;

import com.nhnacademy.minidooraygateway.project.request.MyProjectsPageRequest;
import com.nhnacademy.minidooraygateway.project.respone.ProjectRegisterResponse;
import com.nhnacademy.minidooraygateway.project.respone.ProjectStatusModifyResponse;
import com.nhnacademy.minidooraygateway.project.service.ProjectService;
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
@RequestMapping("projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String showMyProjects(HttpServletRequest request, Model model,
                                 @RequestParam(required = false, value = "size") Long size,
                                 @RequestParam(required = false, value = "page") Long page){
        HttpSession session = request.getSession(false);
        Long userNo = (Long) session.getAttribute("userNo");
        MyProjectsPageRequest myProjectsPage = projectService.findMyProjects(size, page, userNo);

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
    public String registerProject(ProjectRegisterResponse registerRequest){
        projectService.createProject(registerRequest);
        return "redirect:/projects";
    }

    @GetMapping("/{projectNo}/status")
    public String showModifyProjectStatus(@PathVariable String projectNo, Model model){
        model.addAttribute("projectNo", projectNo);
        return "project/status";
    }

    @PostMapping("/{projectNo}/status")
    public String modifyProjectStatus(@PathVariable("projectNo") Long projectNo,
                                      ProjectStatusModifyResponse statusModifyResponse){
        statusModifyResponse.setProjectNo(projectNo);
        projectService.modifyStatus(statusModifyResponse);

        return "redirect:/projects";
    }
}
