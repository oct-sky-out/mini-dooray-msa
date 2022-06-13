package com.nhnacademy.minidoorayprojectmanagementapi.project.repository;

import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.project.request.ProjectStatusModifyRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.QProject;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.QProjectMember;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

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

    @Override
    public Page<ProjectBasicDto> findMyProjects(Long userNo, Pageable pageable) {
        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;

        List<ProjectBasicDto> projectBasics =  from(projectMember)
            .join(projectMember.project, project)
            .where(projectMember.pk.userNo.eq(userNo))
            .select(
                Projections.bean(ProjectBasicDto.class,
                    project.projectNo,
                    project.name,
                    projectMember.id,
                    project.createdAt
                    ))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return PageableExecutionUtils.getPage(projectBasics, pageable, projectBasics::size);
    }
}
