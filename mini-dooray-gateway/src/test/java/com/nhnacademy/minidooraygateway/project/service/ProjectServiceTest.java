package com.nhnacademy.minidooraygateway.project.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidooraygateway.project.request.MyProjectsPageRequest;
import com.nhnacademy.minidooraygateway.project.respone.ProjectRegisterResponse;
import com.nhnacademy.minidooraygateway.project.respone.ProjectCreationSuccessResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectServiceTest {
    @Autowired
    ProjectService projectService;

    @Test
    void findMyProjects() {
        MyProjectsPageRequest result = projectService.findMyProjects(3L, 0L,100L);
        assertThat(result.getProjects()).hasSize(4);
        assertThat(result.getCurrentPage()).isEqualTo(0);
        assertThat(result.isHasNext()).isTrue();
        assertThat(result.isHasPrevious()).isFalse();
    }

    @Test
    void createProject() {
        ProjectRegisterResponse request =new ProjectRegisterResponse();
        request.setProjectName("proj");
        request.setUserId("user1");
        request.setUserNo(100L);

        ProjectCreationSuccessResponse result = projectService.createProject(request);
        assertThat(result.getProject().getName()).isEqualTo("proj");
        assertThat(result.getAdmin().getUserId()).isEqualTo("user1");
        assertThat(result.getAdmin().getUserNo()).isEqualTo(100L);
    }
}
