package com.nhnacademy.minidoorayprojectmanagementapi.comment.controller;

import com.nhnacademy.minidoorayprojectmanagementapi.comment.dto.CommentBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.comment.dto.CommentCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.comment.dto.CommentModifyRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<CommentBasicDto> registerComment(@PathVariable("projectNo") Long projectNo,
                                                           @Validated @RequestBody
                                                           CommentCreationRequest creationRequest) {
        return new ResponseEntity<>(
            commentService.registerComment(projectNo, creationRequest),
            HttpStatus.CREATED);
    }

    @PatchMapping("/{commentNo}")
    public CommentBasicDto registerComment(@PathVariable("projectNo") Long projectNo,
                                           @PathVariable("commentNo") Long commentNo,
                                           @Validated @RequestBody
                                           CommentModifyRequest creationRequest) {
        return commentService.modifyComment(projectNo, commentNo, creationRequest);
    }

    @DeleteMapping("/{commentNo}")
    public CommentBasicDto removeComment(@PathVariable("projectNo") Long projectNo,
                                         @PathVariable("commentNo") Long commentNo){
        return commentService.removeComment(projectNo, commentNo);
    }
}
