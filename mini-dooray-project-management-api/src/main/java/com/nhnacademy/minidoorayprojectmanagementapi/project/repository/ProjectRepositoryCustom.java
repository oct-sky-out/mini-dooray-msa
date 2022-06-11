package com.nhnacademy.minidoorayprojectmanagementapi.project.repository;

import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectExecutionCompleteDto;
import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectStatusModifyRequest;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ProjectRepositoryCustom {
    Long updateProjectStatus(ProjectStatusModifyRequest modifyRequest);
}
