package com.nhnacademy.minidoorayprojectmanagementapi.projectmember.service;

import com.nhnacademy.minidoorayprojectmanagementapi.exceptions.ProjectNotFoundException;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.repository.ProjectRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.dto.ProjectMemberDto;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.repository.ProjectMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectMemberService {
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;

    public ProjectMemberService(ProjectMemberRepository projectMemberRepository,
                                ProjectRepository projectRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public ProjectMemberDto addProjectMember(ProjectMemberDto projectMemberDto) {
        Project project =projectRepository.findById(projectMemberDto.getProjectNo())
            .orElseThrow(ProjectNotFoundException::new);
        ProjectMember.Pk pk = new ProjectMember.Pk(projectMemberDto.getUserNo(), project.getProjectNo());
        ProjectMember projectMember = ProjectMember.builder()
                .pk(pk)
                .id(projectMemberDto.getUserId())
                .project(project)
                .build();

        projectMemberRepository.saveAndFlush(projectMember);

        return projectMemberDto;
    }
}
