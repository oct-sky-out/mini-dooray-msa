package com.nhnacademy.minidoorayprojectmanagementapi.taskTag.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.dto.TaskTagBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.dto.TaskTagRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.dto.TaskTagRegisterRequestList;
import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.service.TaskTagService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TaskTagController.class)
class TaskTagControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskTagService taskTagService;

    @Test
    void registerTagsToTask() throws Exception {
        TaskTagRegisterRequestList requestList =
            getTaskTagRegisterRequestList();

        List<TaskTagBasicDto> responseBody = new ArrayList<>();
        responseBody.add(new TaskTagBasicDto(1L,"tag1",2L,"task2"));
        responseBody.add(new TaskTagBasicDto(2L,"tag2",2L,"task2"));

        given(taskTagService.addTaskTag(1000L, requestList))
            .willReturn(responseBody);

        String json = new ObjectMapper().writeValueAsString(requestList);

        mockMvc.perform(post("/projects/{projectNo}/tasks/tag", 1000L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.[0].tagNo")
                .value(1))
            .andExpect(jsonPath("$.result.[1].tagNo")
                .value(2))
            .andExpect(jsonPath("$.result.[0].taskNo")
                .value(2));
    }

    @Test
    void dropTaskTagsTest() throws Exception {
        TaskTagRegisterRequestList requestList = getTaskTagRegisterRequestList();

        given(taskTagService.dropTaskTag(1000L, requestList))
            .willReturn("success");

        String json = new ObjectMapper().writeValueAsString(requestList);
        mockMvc.perform(put("/projects/{projectNo}/tasks/tag", 1000L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result")
                .value("success"));
    }

    private TaskTagRegisterRequestList getTaskTagRegisterRequestList() {
        TaskTagRegisterRequestList requestList = new TaskTagRegisterRequestList();
        TaskTagRequest request1 = new TaskTagRequest(1L,2L);
        TaskTagRequest request2 = new TaskTagRequest(2L,2L);
        List<TaskTagRequest> taskTagRequests = new ArrayList<>();
        taskTagRequests.add(request1);
        taskTagRequests.add(request2);
        requestList.setTaskTagRequests(taskTagRequests);
        return requestList;
    }
}
