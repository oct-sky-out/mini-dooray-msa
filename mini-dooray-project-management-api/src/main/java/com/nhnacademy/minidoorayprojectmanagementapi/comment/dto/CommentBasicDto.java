package com.nhnacademy.minidoorayprojectmanagementapi.comment.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentBasicDto {
    private Long commentNo;

    private Long authorNo;

    private String authorId;

    private Long taskNo;

    private String taskTitle;

    private String content;

    private LocalDateTime createdAt;
}
