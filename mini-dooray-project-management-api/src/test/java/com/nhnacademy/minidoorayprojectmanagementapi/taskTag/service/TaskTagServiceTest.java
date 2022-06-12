package com.nhnacademy.minidoorayprojectmanagementapi.taskTag.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.dto.TaskTagBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.dto.TaskTagRequest;
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
    void addTaskTagTest() {
        TaskTagRegisterRequestList testRequestList = getTestObject();

        List<TaskTagBasicDto> result =
            taskTagService.addTaskTag(1000L, testRequestList);

        TaskTagRequest request1 = testRequestList.getTaskTagRequests().get(0);
        TaskTagRequest request2 = testRequestList.getTaskTagRequests().get(1);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTagNo()).isEqualTo(request1.getTagNo());
        assertThat(result.get(1).getTagNo()).isEqualTo(request2.getTagNo());
        assertThat(result.get(0).getTaskNo()).isEqualTo(request1.getTaskNo());
    }

    @Test
    void dropTaskTagTest() {
        TaskTagRegisterRequestList testRequestList = getTestObject();
        assertThat(taskTagService.dropTaskTag(1000L, testRequestList))
            .isEqualTo("success");
    }

    private TaskTagRegisterRequestList getTestObject() {
        TaskTagRequest request1 = new TaskTagRequest(3L, 8L);
        TaskTagRequest request2 = new TaskTagRequest(26L, 8L);
        List<TaskTagRequest> taskTagRequests = new ArrayList<>();
        taskTagRequests.add(request1);
        taskTagRequests.add(request2);
        TaskTagRegisterRequestList requestList = new TaskTagRegisterRequestList();
        requestList.setTaskTagRequests(taskTagRequests);

        return requestList;
    }
}
