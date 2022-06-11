package com.nhnacademy.minidoorayprojectmanagementapi.project.repository;

import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectStatusModifyRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.QProject;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ProjectRepositoryImpl extends QuerydslRepositorySupport implements ProjectRepositoryCustom {
    public ProjectRepositoryImpl() {
        super(Project.class);
    }

    @Override
    public Long updateProjectStatus(ProjectStatusModifyRequest modifyRequest) {
        QProject project = QProject.project;
        return update(project)
            .set(project.status, modifyRequest.getProjectStatus())
            .where(project.projectNo.eq(modifyRequest.getProjectNo()))
            .execute();

    }
}
