package com.nhnacademy.minidoorayprojectmanagementapi.project.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.CreationProjectRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectCreationCompleteDto;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.ProjectStatus;
import com.nhnacademy.minidoorayprojectmanagementapi.project.service.ProjectService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ProjectService projectService;

    @Test
    void createProject() throws Exception {
        CreationProjectRequest projectRequest = new CreationProjectRequest();
        projectRequest.setProjectName("project1");
        projectRequest.setUserId("id");
        projectRequest.setUserNo(1L);

        String requestBody = new ObjectMapper().writeValueAsString(projectRequest);

        ProjectCreationCompleteDto projectCreationCompleteDto =
            new ProjectCreationCompleteDto(
                1L,
                "id",
                projectRequest.getProjectName(),
                ProjectStatus.ACTIVE,
                LocalDateTime.now()
            );

        given(projectService.createProject(projectRequest)).willReturn(projectCreationCompleteDto);

        mockMvc.perform(post("/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.projectNo").value(equalTo(1)))
            .andExpect(jsonPath("$.adminId").value(equalTo(projectCreationCompleteDto.getAdminId())))
            .andExpect(jsonPath("$.status").value(equalTo(projectCreationCompleteDto.getStatus().getStatus())))
            .andExpect(jsonPath("$.name").value(equalTo(projectCreationCompleteDto.getName())));
    }
}
