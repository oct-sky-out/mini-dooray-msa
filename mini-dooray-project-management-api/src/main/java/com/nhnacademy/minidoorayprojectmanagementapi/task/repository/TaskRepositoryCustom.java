package com.nhnacademy.minidoorayprojectmanagementapi.task.repository;

import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TaskRepositoryCustom {
    Page<TaskPageDto> findAllTaskByPageable(Pageable pageable);
}
