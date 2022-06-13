package com.nhnacademy.minidoorayprojectmanagementapi.project.repository;


import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.project.request.ProjectStatusModifyRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.ProjectStatus;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.repository.ProjectMemberRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProjectRepositoryTest {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectMemberRepository projectMemberRepository;

    @Test
    void createProjectTest() {

        Project project = Project.builder()
            .name("project1")
            .status(ProjectStatus.ACTIVE)
            .createdAt(LocalDateTime.now())
            .build();

        Project savedProject = projectRepository.save(project);

        ProjectMember.Pk pk = new ProjectMember.Pk();
        pk.setUserNo(1L);

        ProjectMember projectMember = ProjectMember
            .builder()
            .pk(pk)
            .id("id")
            .project(savedProject)
            .build();

        projectMemberRepository.save(projectMember);

        assertThat(savedProject).isEqualTo(project);
    }

    @Test
    void modifyStatusTest() {
        ProjectStatusModifyRequest modifyRequest = new ProjectStatusModifyRequest();
        modifyRequest.setProjectNo(1000L);
        modifyRequest.setProjectStatus(ProjectStatus.END);

        Long result = projectRepository.updateProjectStatus(modifyRequest);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void findMyProjects() {
        Pageable pageable = PageRequest.of(0,5);
        Page<ProjectBasicDto> result = projectRepository.findMyProjects(100L, pageable);

        assertThat(result.hasContent()).isTrue();
        assertThat(result).hasSize(6);
        assertThat(result.hasNext()).isTrue();
    }
}
