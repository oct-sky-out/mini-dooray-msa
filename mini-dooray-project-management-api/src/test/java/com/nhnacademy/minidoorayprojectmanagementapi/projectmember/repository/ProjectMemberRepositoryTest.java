package com.nhnacademy.minidoorayprojectmanagementapi.projectmember.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.repository.ProjectRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProjectMemberRepositoryTest {
    @Autowired
    EntityManager entityManager;

    @Autowired
    ProjectMemberRepository projectMemberRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    void addProjectMemberTest() {
        Project project = Project.builder()
            .name("project1")
            .build();
        entityManager.persist(project);


        ProjectMember.Pk pk = new ProjectMember.Pk(100L, project.getProjectNo());
        ProjectMember projectMember = ProjectMember.builder()
            .pk(pk)
            .id("user")
            .project(project)
            .build();

        ProjectMember member = projectMemberRepository.save(projectMember);

        assertThat(member.getProject().getName()).isEqualTo(project.getName());
        assertThat(member.getId()).isEqualTo(projectMember.getId());
    }

}
