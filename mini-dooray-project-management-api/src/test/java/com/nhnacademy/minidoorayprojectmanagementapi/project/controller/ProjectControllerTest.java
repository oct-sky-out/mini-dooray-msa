package com.nhnacademy.minidoorayprojectmanagementapi.project.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.CreationProjectRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectExecutionCompleteDto;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.ProjectStatus;
import com.nhnacademy.minidoorayprojectmanagementapi.project.service.ProjectService;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.dto.ProjectMemberDto;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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

        ProjectExecutionCompleteDto projectExecutionCompleteDto =
            new ProjectExecutionCompleteDto(
                1L,
                projectRequest.getProjectName(),
                ProjectStatus.ACTIVE,
                LocalDateTime.now()
            );
        ProjectMemberDto admin = new ProjectMemberDto(
            projectRequest.getUserNo(),
            projectRequest.getUserId(),
            projectExecutionCompleteDto.getProjectNo());

        Map<String, Object> result = new HashMap<>();
        result.put("project", projectExecutionCompleteDto);
        result.put("admin", admin);

        given(projectService.createProject(projectRequest)).willReturn(result);

        mockMvc.perform(post("/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.project.projectNo").value(equalTo(1)))
            .andExpect(jsonPath("$.admin.userId").value(equalTo(admin.getUserId())))
            .andExpect(jsonPath("$.project.status").value(equalTo(projectExecutionCompleteDto.getStatus().getStatus())))
            .andExpect(jsonPath("$.project.name").value(equalTo(projectExecutionCompleteDto.getName())));
    }
}
