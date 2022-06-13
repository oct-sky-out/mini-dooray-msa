package com.nhnacademy.minidoorayprojectmanagementapi.project.controller;

import com.nhnacademy.minidoorayprojectmanagementapi.project.request.CreationProjectRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectExecutionCompleteDto;
import com.nhnacademy.minidoorayprojectmanagementapi.project.request.ProjectStatusModifyRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.project.response.MyProjectsPageResponse;
import com.nhnacademy.minidoorayprojectmanagementapi.project.service.ProjectService;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createProject(@Validated @RequestBody CreationProjectRequest projectRequest){
        return projectService.createProject(projectRequest);
    }

    @PatchMapping("/{id}/status")
    public ProjectExecutionCompleteDto modifyProjectStatus(@Validated @RequestBody
                                                           ProjectStatusModifyRequest modifyRequest){
        return projectService.modifyProjectStatus(modifyRequest);
    }

    @GetMapping("/{userNo}")
    public MyProjectsPageResponse findMyAllProjects(@PathVariable("userNo") Long userNo,
                                                    Pageable pageable){
        return projectService.findMyProjects(userNo, pageable);
    }
}
