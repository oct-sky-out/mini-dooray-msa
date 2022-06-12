package com.nhnacademy.minidoorayprojectmanagementapi.advisor.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidoorayprojectmanagementapi.exceptions.TaskNotFoundException;
import com.nhnacademy.minidoorayprojectmanagementapi.task.controller.TaskController;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TaskController.class)
class ExceptionAdvisorControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskService taskService;

    @Test
    void handleValidationExceptionsTest() throws Exception {
        TaskCreationRequest creationRequest = new TaskCreationRequest();
        creationRequest.setTitle("");
        creationRequest.setContent("error!");
        creationRequest.setAuthorNo(null);
        creationRequest.setProjectNo(1L);

        String errorJson = new ObjectMapper().writeValueAsString(creationRequest);

        mockMvc.perform(post("/projects/{projectNo}/tasks", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(errorJson))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.title").value("must not be blank"))
            .andExpect(jsonPath("$.authorNo").value("must not be null"));
    }

    @Test
    void handleAnyExceptionsTest() throws Exception {
        given(taskService.findDetailTask(1L, 10L))
            .willThrow(new TaskNotFoundException());

        mockMvc.perform(get("/projects/{projectNo}/tasks/{taskNo}", 1L, 10L)
            .queryParam("page", "0")
            .queryParam("size", "10"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.reason")
                .value("업무를 찾을 수 없습니다."));
    }
}
