package com.nhnacademy.minidoorayprojectmanagementapi.project.service;

import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.CreationProjectRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectCreationCompleteDto;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.ProjectStatus;
import com.nhnacademy.minidoorayprojectmanagementapi.project.repository.ProjectRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.repository.ProjectMemberRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public ProjectService(ProjectRepository projectRepository,
                          ProjectMemberRepository projectMemberRepository) {
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    public ProjectCreationCompleteDto createProject(CreationProjectRequest projectRequest) {
        ProjectMember admin = ProjectMember.builder()
            .id(projectRequest.getUserId())
            .userNo(projectRequest.getUserNo())
            .build();

        Project project = Project.builder()
            .name(projectRequest.getProjectName())
            .status(ProjectStatus.ACTIVE)
            .admin(admin)
            .createdAt(LocalDateTime.now())
            .build();

        admin.setProject(project);

        Project savedProject = projectRepository.save(project);
        projectMemberRepository.save(admin);

        return new ProjectCreationCompleteDto(
            savedProject.getProjectNo(),
            admin.getId(),
            savedProject.getName(),
            savedProject.getStatus(),
            savedProject.getCreatedAt()
            );
    }
}
