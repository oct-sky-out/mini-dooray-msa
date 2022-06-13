package com.nhnacademy.minidoorayprojectmanagementapi.project.service;

import com.nhnacademy.minidoorayprojectmanagementapi.exceptions.ProjectNotFoundException;
import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.project.request.CreationProjectRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectExecutionCompleteDto;
import com.nhnacademy.minidoorayprojectmanagementapi.project.request.ProjectStatusModifyRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.ProjectStatus;
import com.nhnacademy.minidoorayprojectmanagementapi.project.repository.ProjectRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.project.response.MyProjectsPageResponse;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.dto.ProjectMemberDto;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.repository.ProjectMemberRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Map<String,Object> createProject(CreationProjectRequest projectRequest) {
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
        ProjectMember admin = projectMemberRepository.saveAndFlush(projectMember);
        ProjectMemberDto adminInfo = new ProjectMemberDto(admin.pk.getUserNo(), admin.getId(), admin.pk.getProjectNo());

        Map<String, Object> result = new HashMap<>();
        ProjectExecutionCompleteDto projectInfo = new ProjectExecutionCompleteDto(
            savedProject.getProjectNo(),
            savedProject.getName(),
            savedProject.getStatus(),
            savedProject.getCreatedAt()
        );
        result.put("project", projectInfo);
        result.put("admin", adminInfo);

        return result;
    }

    @Transactional
    public ProjectExecutionCompleteDto modifyProjectStatus(ProjectStatusModifyRequest modifyRequest) {
        projectRepository.updateProjectStatus(modifyRequest);
        Project project = projectRepository.findById(modifyRequest.getProjectNo())
            .orElseThrow(ProjectNotFoundException::new);

        return new ProjectExecutionCompleteDto(
            project.getProjectNo(),
            project.getName(),
            project.getStatus(),
            project.getCreatedAt()
        );
    }

    public MyProjectsPageResponse findMyProjects(Long userNo, Pageable pageable){
        Page<ProjectBasicDto> projectPage = projectRepository.findMyProjects(userNo, pageable);

        return new MyProjectsPageResponse(
            projectPage.getContent(),
            projectPage.hasNext(),
            projectPage.hasPrevious(),
            projectPage.getNumber()
        );

    }
}
