package com.nhnacademy.minidoorayprojectmanagementapi.projectmember.service;

import static com.nhnacademy.minidoorayprojectmanagementapi.projectmember.Dummy.getTestDummyProject;
import static com.nhnacademy.minidoorayprojectmanagementapi.projectmember.Dummy.getTestDummyProjectAdmin;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.repository.ProjectRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.Dummy;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.dto.projectmember.ProjectMemberDto;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.repository.ProjectMemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ProjectMemberServiceTest {
    @Autowired
    ProjectMemberService projectMemberService;

    @MockBean
    ProjectMemberRepository projectMemberRepository;

    @MockBean
    ProjectRepository projectRepository;

    @Test
    void addProjectMember() {
        ProjectMemberDto projectMemberDto = new ProjectMemberDto();
        projectMemberDto.setProjectNo(1L);
        projectMemberDto.setUserNo(2L);
        projectMemberDto.setUserId("user2");

        ProjectMember projectAdmin = getTestDummyProjectAdmin(projectMemberDto);
        Project project = getTestDummyProject(projectAdmin);


        ProjectMember projectMember = ProjectMember.builder()
                .pk(new ProjectMember.Pk(projectMemberDto.getUserNo(), project.getProjectNo()))
                .id(projectMemberDto.getUserId())
                .project(project)
                .build();

        given(projectRepository.findById(projectMemberDto.getProjectNo()))
            .willReturn(Optional.of(project));

        given(projectMemberRepository.save(projectMember))
            .willReturn(projectMember);

        ProjectMemberDto result = projectMemberService.addProjectMember(projectMemberDto);
        assertThat(result.getProjectNo()).isEqualTo(projectMemberDto.getProjectNo());
        assertThat(result.getUserId()).isEqualTo(projectMemberDto.getUserId());
        assertThat(result.getUserNo()).isEqualTo(projectMemberDto.getUserNo());

        verify(projectRepository, times(1)).findById(projectMemberDto.getProjectNo());
        verify(projectMemberRepository, times(1)).save(projectMember);
    }
}
