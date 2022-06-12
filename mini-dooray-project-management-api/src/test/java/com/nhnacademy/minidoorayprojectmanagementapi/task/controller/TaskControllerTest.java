package com.nhnacademy.minidoorayprojectmanagementapi.task.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskExecutionCompleteDto;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskModifyRequest;
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

    @Test
    void taskModifyTest() throws Exception {
        TaskModifyRequest modifyRequest = new TaskModifyRequest();
        modifyRequest.setTaskNo(199L);
        modifyRequest.setTitle("ti1");
        modifyRequest.setContent("con1");

        given(taskService.modifyTask(modifyRequest))
            .willReturn(TaskExecutionCompleteDto.builder()
                .taskNo(modifyRequest.getTaskNo())
                .content(modifyRequest.getContent())
                .title(modifyRequest.getTitle())
                .projectNo(10L)
                .author(12L)
                .createdAt(LocalDateTime.now())
                .build());

        String json = new ObjectMapper().writeValueAsString(modifyRequest);
        mockMvc.perform(put("/projects/{id}/tasks", 199)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.title")
                .value(equalTo(modifyRequest.getTitle())))
            .andExpect(jsonPath("$.content")
                .value(equalTo(modifyRequest.getContent())))
            .andExpect(jsonPath("$.taskNo")
                .value(equalTo(modifyRequest.getTaskNo().intValue())));
    }

    @Test
    void taskDeleteTest() throws Exception {
        given(taskService.removeTask(1L))
            .willReturn(
                TaskExecutionCompleteDto.builder()
                    .taskNo(1L)
                    .content("con")
                    .title("tit")
                    .projectNo(10L)
                    .author(12L)
                    .createdAt(LocalDateTime.now())
                    .build()
            );

        mockMvc.perform(delete("/projects/{id}/tasks/{taskNo}", 199, 1))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.taskNo").value(equalTo(1)));
    }
}
