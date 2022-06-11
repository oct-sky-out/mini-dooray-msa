package com.nhnacademy.minidoorayprojectmanagementapi.project.service;

import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.CreationProjectRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectExecutionCompleteDto;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.ProjectStatus;
import com.nhnacademy.minidoorayprojectmanagementapi.project.repository.ProjectRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.repository.ProjectMemberRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public ProjectService(ProjectRepository projectRepository,
                          ProjectMemberRepository projectMemberRepository) {
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    @Transactional
    public ProjectExecutionCompleteDto createProject(CreationProjectRequest projectRequest) {
        Project project = Project.builder()
            .name(projectRequest.getProjectName())
            .status(ProjectStatus.ACTIVE)
            .createdAt(LocalDateTime.now())
            .build();

        Project savedProject = projectRepository.saveAndFlush(project);

        ProjectMember.Pk pk = new ProjectMember.Pk(projectRequest.getUserNo(), savedProject.getProjectNo());
        ProjectMember projectMember = ProjectMember
            .builder()
            .pk(pk)
            .project(savedProject)
            .id(projectRequest.getUserId())
            .isAdmin(false)
            .build();
        ProjectMember savedAdmin = projectMemberRepository.saveAndFlush(projectMember);

        return new ProjectExecutionCompleteDto(
            savedProject.getProjectNo(),
            savedAdmin.getId(),
            savedProject.getName(),
            savedProject.getStatus(),
            savedProject.getCreatedAt()
            );
    }
}
