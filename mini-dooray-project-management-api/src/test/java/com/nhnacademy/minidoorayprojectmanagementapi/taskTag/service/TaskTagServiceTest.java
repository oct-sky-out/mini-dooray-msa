package com.nhnacademy.minidoorayprojectmanagementapi.taskTag.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.dto.TaskTagBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.dto.TaskTagRegisterRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.dto.TaskTagRegisterRequestList;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class TaskTagServiceTest {
    @Autowired
    TaskTagService taskTagService;

    @Test
    void addTaskTag() {
        TaskTagRegisterRequest request1 = new TaskTagRegisterRequest(3L, 8L);
        TaskTagRegisterRequest request2 = new TaskTagRegisterRequest(26L, 8L);

        List<TaskTagRegisterRequest> taskTagRegisterRequests = new ArrayList<>();
        taskTagRegisterRequests.add(request1);
        taskTagRegisterRequests.add(request2);
        TaskTagRegisterRequestList requestList = new TaskTagRegisterRequestList();
        requestList.setTaskTagRegisterRequests(taskTagRegisterRequests);

        List<TaskTagBasicDto> result =
            taskTagService.addTaskTag(1000L, requestList);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTagNo()).isEqualTo(request1.getTagNo());
        assertThat(result.get(1).getTagNo()).isEqualTo(request2.getTagNo());
        assertThat(result.get(0).getTaskNo()).isEqualTo(request1.getTaskNo());
    }
}
