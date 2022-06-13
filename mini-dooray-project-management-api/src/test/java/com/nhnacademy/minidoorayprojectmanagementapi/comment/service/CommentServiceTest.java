package com.nhnacademy.minidoorayprojectmanagementapi.comment.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayprojectmanagementapi.comment.dto.CommentBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.comment.dto.CommentCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.task.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CommentServiceTest {
    @Autowired
    CommentService commentService;

    @Autowired
    TaskRepository taskRepository;

    @Test
    void registerComment() {
        CommentCreationRequest commentCreationRequest = new CommentCreationRequest();
        commentCreationRequest.setAuthor(100L);
        commentCreationRequest.setTaskNo(8L);
        commentCreationRequest.setContent("CONTENT@@@@!!");

        CommentBasicDto result = commentService.registerComment(commentCreationRequest);

        assertThat(result.getContent()).isEqualTo(commentCreationRequest.getContent());
        assertThat(result.getAuthorId()).isEqualTo("anonymous3");
        assertThat(result.getAuthorNo()).isEqualTo(commentCreationRequest.getAuthor());
        assertThat(result.getTaskNo()).isEqualTo(commentCreationRequest.getTaskNo());
    }
}