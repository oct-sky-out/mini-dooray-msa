package com.nhnacademy.minidoorayprojectmanagementapi.project.repository;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ProjectRepositoryCustom {
    void updateProjectStatus();
}
