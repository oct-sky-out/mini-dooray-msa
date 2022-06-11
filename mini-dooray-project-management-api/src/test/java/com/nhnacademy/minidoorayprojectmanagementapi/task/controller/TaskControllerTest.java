package com.nhnacademy.minidoorayprojectmanagementapi.task.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskExecutionCompleteDto;
import com.nhnacademy.minidoorayprojectmanagementapi.task.service.TaskService;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskService taskService;

    @Test
    void createNewTask() throws Exception {
        TaskCreationRequest request = new TaskCreationRequest();
        request.setProjectNo(1L);
        request.setAuthorNo(1L);
        request.setTitle("title");
        request.setContent("contnet!");

        String requestBody = new ObjectMapper().writeValueAsString(request);
        given(taskService.createTask(request))
            .willReturn(TaskExecutionCompleteDto.builder()
                .taskNo(1L)
                .content(request.getContent())
                .title(request.getTitle())
                .projectNo(request.getProjectNo())
                .author(request.getAuthorNo())
                .createdAt(LocalDateTime.now())
                .build());

        mockMvc.perform(post("/projects/{id}/tasks", request.getProjectNo())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.projectNo")
                .value(equalTo(request.getProjectNo().intValue())))
            .andExpect(jsonPath("$.author")
                .value(equalTo(request.getAuthorNo().intValue())))
            .andExpect(jsonPath("$.title")
                .value(equalTo(request.getTitle())))
            .andExpect(jsonPath("$.content")
                .value(equalTo(request.getContent())));
    }
}
