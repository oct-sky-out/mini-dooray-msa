package com.nhnacademy.minidoorayprojectmanagementapi.task.repository;

import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>, TaskRepositoryCustom{
}
