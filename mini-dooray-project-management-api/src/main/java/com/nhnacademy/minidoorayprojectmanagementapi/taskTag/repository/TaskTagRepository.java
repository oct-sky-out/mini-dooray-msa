package com.nhnacademy.minidoorayprojectmanagementapi.taskTag.repository;

import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.entity.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTagRepository extends JpaRepository<TaskTag, TaskTag.Pk>, TaskTagRepositoryCustom {
}
