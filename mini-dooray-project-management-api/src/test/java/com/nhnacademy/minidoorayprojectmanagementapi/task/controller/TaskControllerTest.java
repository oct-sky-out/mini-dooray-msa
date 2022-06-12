package com.nhnacademy.minidoorayprojectmanagementapi.task.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.Collection;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskDetailResponse;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskExecutionCompleteDto;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskModifyRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskPageResponse;
import com.nhnacademy.minidoorayprojectmanagementapi.task.service.TaskService;
import java.time.LocalDateTime;
import java.util.Collections;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Test
    void displayTaskPageTest() throws Exception {
        Long projectNo = 100L;
        Pageable pageable = PageRequest.of(0,5);
        given(taskService.getTaskPage(projectNo, pageable))
            .willReturn(new TaskPageResponse(
                Collections.emptyList(),
                false,
                false,
                0));

        mockMvc.perform(get("/projects/{id}/tasks", 100)
                .param("size", "5")
                .param("page", "0"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content")
                .value(equalTo(Collections.emptyList())))
            .andExpect(jsonPath("$.hasNextPage")
                .value(equalTo(false)))
            .andExpect(jsonPath("$.hasPreviousPage")
                .value(equalTo(false)))
            .andExpect(jsonPath("$.currentPage")
                .value(equalTo(0)));
    }

    @Test
    void displayTaskDetailTet() throws Exception {
        TaskDetailResponse detailResponse = new TaskDetailResponse();
        detailResponse.setTaskNo(8L);
        detailResponse.setProject(null);
        detailResponse.setMilestone(null);
        detailResponse.setAuthor("author1");
        detailResponse.setTitle("title");
        detailResponse.setContent("content");
        detailResponse.setCreatedAt(LocalDateTime.now());

        given(taskService.findDetailTask(1000L, 8L))
            .willReturn(detailResponse);

        mockMvc.perform(get("/projects/{taskNo}/tasks/{taskNo}", 1000, 8L))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.taskNo")
                .value(equalTo(8)))
            .andExpect(jsonPath("$.author")
                .value(equalTo("author1")))
            .andExpect(jsonPath("$.title")
                .value(equalTo("title")))
            .andExpect(jsonPath("$.content")
                .value(equalTo("content")));
    }
}
