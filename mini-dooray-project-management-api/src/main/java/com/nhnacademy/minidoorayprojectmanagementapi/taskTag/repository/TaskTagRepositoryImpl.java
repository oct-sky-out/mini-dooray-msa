package com.nhnacademy.minidoorayprojectmanagementapi.taskTag.repository;

import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.entity.QTaskTag;
import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.entity.TaskTag;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class TaskTagRepositoryImpl extends QuerydslRepositorySupport implements TaskTagRepositoryCustom {
    public TaskTagRepositoryImpl() {
        super(TaskTag.class);
    }


    @Override
    public int dropTaskTag(List<TaskTag.Pk> pkList) {
        QTaskTag taskTag = QTaskTag.taskTag;

        delete(taskTag)
            .where(taskTag.pk.in(pkList))
            .execute();

        return pkList.size();
    }
}
