package com.nhnacademy.minidoorayprojectmanagementapi.project.repository;

import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.project.request.ProjectStatusModifyRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ProjectRepositoryCustom {
    Long updateProjectStatus(ProjectStatusModifyRequest modifyRequest);

    Page<ProjectBasicDto> findMyProjects(Long userNo, Pageable pageable);
}
