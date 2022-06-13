package com.nhnacademy.minidoorayprojectmanagementapi.comment.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidoorayprojectmanagementapi.comment.dto.CommentBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.comment.dto.CommentCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.comment.dto.CommentModifyRequest;
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

        given(commentService.registerComment(1L, commentCreationRequest))
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
            .registerComment(1L, commentCreationRequest);
    }

    @Test
    void modifyCommentTest() throws Exception {
        CommentModifyRequest commentModifyRequest = new CommentModifyRequest();
        commentModifyRequest.setContent("modify!");

        CommentBasicDto commentBasicDto = new CommentBasicDto(
            6L,
            1L,
            "author1",
            1L,
            "task1",
            commentModifyRequest.getContent(),
            LocalDateTime.now()
        );

        given(commentService.modifyComment(1000L, 6L, commentModifyRequest))
            .willReturn(commentBasicDto);

        String json = new ObjectMapper().writeValueAsString(commentModifyRequest);
        mockMvc.perform(patch("/projects/{projectNo}/tasks/{taskNo}/comments/{commentNo}", 1000, 1, 6)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content")
                .value(equalTo(commentBasicDto.getContent())))
            .andExpect(jsonPath("$.commentNo")
                .value(equalTo(commentBasicDto.getCommentNo().intValue())))
            .andExpect(jsonPath("$.authorNo")
                .value(equalTo(commentBasicDto.getAuthorNo().intValue())))
            .andExpect(jsonPath("$.taskNo")
                .value(equalTo(commentBasicDto.getTaskNo().intValue())));


        verify(commentService, times(1))
            .modifyComment(1000L, 6L, commentModifyRequest);
    }

    @Test
    void removeCommentTest() throws Exception {
        Long projectNo = 1000L;
        Long commentNo = 6L;

        CommentBasicDto commentBasicDto = new CommentBasicDto(
            commentNo,
            1L,
            "author1",
            1L,
            "task1",
            "content",
            LocalDateTime.now()
        );

        given(commentService.removeComment(projectNo, commentNo))
            .willReturn(commentBasicDto);

        mockMvc.perform(delete("/projects/{projectNo}/tasks/{taskNo}/comments/{commentNo}", 1000, 1, 6))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.commentNo").value(equalTo(commentNo.intValue())))
            .andDo(print());
    }
}
