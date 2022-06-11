package com.nhnacademy.minidoorayprojectmanagementapi.project.repository;

import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectStatusModifyRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {
    @Override
    @Modifying
    @Transactional
    Long updateProjectStatus(ProjectStatusModifyRequest modifyRequest);
}
