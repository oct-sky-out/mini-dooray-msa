package com.nhnacademy.minidoorayprojectmanagementapi.taskTag.repository;

import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.entity.TaskTag;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TaskTagRepositoryCustom {
    int dropTaskTag(List<TaskTag.Pk> pkList);
}
