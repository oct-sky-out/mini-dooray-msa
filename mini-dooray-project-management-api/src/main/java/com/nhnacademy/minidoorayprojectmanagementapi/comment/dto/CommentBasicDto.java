package com.nhnacademy.minidoorayprojectmanagementapi.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
