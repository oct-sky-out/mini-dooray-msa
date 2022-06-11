package com.nhnacademy.minidoorayprojectmanagementapi.project.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.CreationProjectRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectExecutionCompleteDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProjectServiceTest {
    @Autowired
    ProjectService projectService;

    @Test
    void createProjectTest() {
        CreationProjectRequest projectRequest = new CreationProjectRequest();
        projectRequest.setProjectName("project1");
        projectRequest.setUserId("id");
        projectRequest.setUserNo(1L);

        ProjectExecutionCompleteDto saved = projectService.createProject(projectRequest);

        assertThat(saved.getName()).isEqualTo(projectRequest.getProjectName());
        assertThat(saved.getAdminId()).isEqualTo(projectRequest.getUserId());
    }
}
