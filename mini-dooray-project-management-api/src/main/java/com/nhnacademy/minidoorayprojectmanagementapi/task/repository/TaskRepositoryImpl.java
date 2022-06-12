package com.nhnacademy.minidoorayprojectmanagementapi.task.repository;

import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskPageDto;
import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.QTask;
import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.Task;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

public class TaskRepositoryImpl extends QuerydslRepositorySupport implements TaskRepositoryCustom {
    public TaskRepositoryImpl() {
        super(Task.class);
    }

    @Override
    public Page<TaskPageDto> findAllTaskByPageable(Pageable pageable) {
        QTask task = QTask.task;

        List<TaskPageDto> tasks = from(task)
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
            .limit(pageable.getPageSize())
            .fetch();

        return PageableExecutionUtils.getPage(tasks, pageable, tasks::size);
    }
}
