package com.nhnacademy.minidoorayprojectmanagementapi.projectmember.controller;

import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.dto.ProjectMemberDto;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.service.ProjectMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects/members")
public class ProjectMemberController {
    private final ProjectMemberService projectMemberService;

    public ProjectMemberController(ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectMemberDto addProjectMember(@Validated @RequestBody
                                             ProjectMemberDto projectMemberDto) {
        return projectMemberService.addProjectMember(projectMemberDto);
    }
}
