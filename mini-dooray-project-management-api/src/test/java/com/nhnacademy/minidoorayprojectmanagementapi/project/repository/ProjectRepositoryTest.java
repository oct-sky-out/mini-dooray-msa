package com.nhnacademy.minidoorayprojectmanagementapi.project.repository;


import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.ProjectStatus;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.repository.ProjectMemberRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProjectRepositoryTest {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectMemberRepository projectMemberRepository;

    @Test
    void createProjectTest() {
        ProjectMember projectMember = ProjectMember
            .builder()
            .userNo(1L)
            .id("id")
            .build();

        Project project = Project.builder()
            .name("project1")
            .admin(projectMember)
            .status(ProjectStatus.ACTIVE)
            .createdAt(LocalDateTime.now())
            .build();

        Project savedProject = projectRepository.save(project);

        projectMember.setProject(project);
        projectMemberRepository.save(projectMember);

        assertThat(savedProject).isEqualTo(project);
    }
}
