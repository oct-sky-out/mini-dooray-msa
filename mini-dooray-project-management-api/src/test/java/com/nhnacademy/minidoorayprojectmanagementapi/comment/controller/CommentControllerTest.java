package com.nhnacademy.minidoorayprojectmanagementapi.comment.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidoorayprojectmanagementapi.comment.dto.CommentBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.comment.dto.CommentCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.comment.service.CommentService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CommentController.class)
class CommentControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommentService commentService;

    @Test
    void registerCommentTest () throws Exception {
        CommentCreationRequest commentCreationRequest = new CommentCreationRequest();
        commentCreationRequest.setContent("content");
        commentCreationRequest.setAuthor(1L);
        commentCreationRequest.setTaskNo(10L);

        CommentBasicDto commentBasicDto = new CommentBasicDto(
            1L,
            commentCreationRequest.getAuthor(),
            "author1",
            commentCreationRequest.getTaskNo(),
            "task1",
            commentCreationRequest.getContent(),
            LocalDateTime.now()
        );

        given(commentService.registerComment(commentCreationRequest))
            .willReturn(commentBasicDto);

        String json = new ObjectMapper().writeValueAsString(commentCreationRequest);
        mockMvc.perform(post("/projects/{projectNo}/tasks/{taskNo}/comments", 1, 10)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.commentNo")
                .value(equalTo(1)))
            .andExpect(jsonPath("$.taskNo")
                .value(equalTo(commentBasicDto.getTaskNo().intValue())))
            .andExpect(jsonPath("$.authorNo")
                .value(equalTo(commentBasicDto.getAuthorNo().intValue())))
            .andExpect(jsonPath("$.content")
                .value(equalTo(commentBasicDto.getContent())));

        verify(commentService, times(1))
            .registerComment(commentCreationRequest);
    }
}
