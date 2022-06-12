package com.nhnacademy.minidoorayprojectmanagementapi.task.repository;

import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskDetailResponse;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskPageDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TaskRepositoryCustom {
    Page<TaskPageDto> findAllTaskByPageable(Long projectNo, Pageable pageable);

    Optional<TaskDetailResponse> findTaskDetail(Long projectNo, Long taskNo);
}
