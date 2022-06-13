package com.nhnacademy.minidoorayprojectmanagementapi.project.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayprojectmanagementapi.project.request.CreationProjectRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectExecutionCompleteDto;
import com.nhnacademy.minidoorayprojectmanagementapi.project.request.ProjectStatusModifyRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.ProjectStatus;
import com.nhnacademy.minidoorayprojectmanagementapi.project.response.MyProjectsPageResponse;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.dto.ProjectMemberDto;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        Map<String, Object> saved = projectService.createProject(projectRequest);

        ProjectExecutionCompleteDto executionCompleteDto =
            (ProjectExecutionCompleteDto) saved.get("project");
        ProjectMemberDto admin = (ProjectMemberDto) saved.get("admin");
        assertThat(executionCompleteDto.getName()).isEqualTo(projectRequest.getProjectName());
        assertThat(admin.getUserId()).isEqualTo(projectRequest.getUserId());
    }

    @Test
    void modifyProjectStatusTest() {
        ProjectStatusModifyRequest modifyRequest = new ProjectStatusModifyRequest();
        modifyRequest.setProjectStatus(ProjectStatus.END);
        modifyRequest.setProjectNo(7L);

        ProjectExecutionCompleteDto executionCompleteDto = projectService.modifyProjectStatus(modifyRequest);

        assertThat(executionCompleteDto.getProjectNo()).isEqualTo(modifyRequest.getProjectNo());
        assertThat(executionCompleteDto.getStatus()).isEqualTo(modifyRequest.getProjectStatus());
    }

    @Test
    void findMyProjectsTest() {
        Pageable pageable = PageRequest.of(0, 5);
        MyProjectsPageResponse projectsPageResponse = projectService.findMyProjects(100L, pageable);

        assertThat(projectsPageResponse.getCurrentPage()).isEqualTo(0);
        assertThat(projectsPageResponse.getProjects()).isNotEmpty();
        assertThat(projectsPageResponse.isHasNext()).isTrue();
    }
}
