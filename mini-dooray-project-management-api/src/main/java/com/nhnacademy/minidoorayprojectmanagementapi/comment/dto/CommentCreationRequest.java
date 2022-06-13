package com.nhnacademy.minidoorayprojectmanagementapi.comment.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentCreationRequest {
    @NotBlank
    private String content;

    @NotNull
    private Long author;

    @NotNull
    private Long taskNo;
}
