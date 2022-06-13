package com.nhnacademy.minidoorayprojectmanagementapi.comment.controller;

import com.nhnacademy.minidoorayprojectmanagementapi.comment.dto.CommentBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.comment.dto.CommentCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects/{projectNo}/tasks/{taskNo}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentBasicDto> registerComment(@Validated @RequestBody
                                                           CommentCreationRequest creationRequest) {
        return new ResponseEntity<>(
            commentService.registerComment(creationRequest),
            HttpStatus.CREATED);
    }
}
