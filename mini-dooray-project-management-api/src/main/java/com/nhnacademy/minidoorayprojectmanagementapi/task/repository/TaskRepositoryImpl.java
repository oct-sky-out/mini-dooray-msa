package com.nhnacademy.minidoorayprojectmanagementapi.task.repository;

import com.nhnacademy.minidoorayprojectmanagementapi.milestone.dto.MilestoneBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.entity.Milestone;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.entity.QMilestone;
import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectExecutionCompleteDto;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.QProject;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.QProjectMember;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskDetailResponse;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskPageDto;
import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.QTask;
import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.Task;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

public class TaskRepositoryImpl extends QuerydslRepositorySupport implements TaskRepositoryCustom {
    public TaskRepositoryImpl() {
        super(Task.class);
    }

    @Override
    public Page<TaskPageDto> findAllTaskByPageable(Long projectNo, Pageable pageable) {
        QTask task = QTask.task;

        List<TaskPageDto> tasks = from(task)
            .where(task.project.projectNo.eq(projectNo))
            .select(
                Projections.bean(TaskPageDto.class,
                    task.taskNo,
                    task.project.projectNo,
                    task.author,
                    task.mileStone.milestoneNo,
                    task.title,
                    task.createdAt
                    ))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize() + 1)
            .fetch();

        return PageableExecutionUtils.getPage(tasks, pageable, tasks::size);
    }

    @Override
    public Optional<TaskDetailResponse> findTaskDetail(Long projectNo, Long taskNo) {
        QTask task = QTask.task;
        QProjectMember projectMember = QProjectMember.projectMember;
        QMilestone mileStone = QMilestone.milestone;

        TaskDetailResponse result = from(task)
            .join(projectMember)
                .on(task.author.eq(projectMember.pk.userNo)
                    .and(task.project.projectNo.eq(projectMember.pk.projectNo)))
            .leftJoin(task.mileStone, mileStone)
            .where(task.project.projectNo.eq(projectNo)
                .and(task.taskNo.eq(taskNo)))
            .select(Projections.bean(TaskDetailResponse.class,
                task.taskNo,
                Projections.constructor(ProjectExecutionCompleteDto.class,
                    task.project.projectNo,
                    task.project.name,
                    task.project.status,
                    task.project.createdAt
                    ).as("project"),
                Projections.constructor(MilestoneBasicDto.class,
                        mileStone.milestoneNo,
                        mileStone.name,
                        mileStone.start,
                        mileStone.end,
                        mileStone.endStatus
                    ).as("milestone"),
                projectMember.id.as("author"),
                task.title,
                task.content,
                task.createdAt
            ))
            .distinct()
            .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Long registerMilestone(Long projectNo, Long taskNo, Long milestoneNo) {
        QTask task = QTask.task;
        QMilestone milestone = QMilestone.milestone;

        Milestone registerTarget = from(milestone)
            .where(milestone.milestoneNo.eq(milestoneNo)
                .and(milestone.project.projectNo.eq(projectNo)))
            .select(milestone)
            .fetchOne();

        return update(task)
            .where(task.taskNo.eq(taskNo).and(task.project.projectNo.eq(projectNo)))
            .set(task.mileStone, registerTarget)
            .execute();
    }

    @Override
    public Long dropMilestone(Long projectNo, Long taskNo) {
        QTask task = QTask.task;

        return update(task)
            .where(task.taskNo.eq(taskNo).and(task.project.projectNo.eq(projectNo)))
            .setNull(task.mileStone)
            .execute();
    }
}
