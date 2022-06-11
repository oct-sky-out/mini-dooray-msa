package com.nhnacademy.minidoorayprojectmanagementapi.projectmember.service;

import com.nhnacademy.minidoorayprojectmanagementapi.exceptions.ProjectNotFoundException;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.repository.ProjectRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.dto.projectmember.ProjectMemberDto;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.repository.ProjectMemberRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectMemberService {
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;

    public ProjectMemberService(ProjectMemberRepository projectMemberRepository,
                                ProjectRepository projectRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
    }

    public ProjectMemberDto addProjectMember(ProjectMemberDto projectMemberDto) {
        Project project =projectRepository.findById(projectMemberDto.getProjectNo())
            .orElseThrow(ProjectNotFoundException::new);

        ProjectMember projectMember = ProjectMember.builder()
                .id(projectMemberDto.getUserId())
                .userNo(projectMemberDto.getUserNo())
                .project(project)
                .build();

        projectMemberRepository.save(projectMember);

        return projectMemberDto;
    }
}
