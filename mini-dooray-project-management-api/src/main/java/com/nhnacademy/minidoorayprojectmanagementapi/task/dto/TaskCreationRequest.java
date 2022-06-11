package com.nhnacademy.minidoorayprojectmanagementapi.task.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskCreationRequest {
    @NotNull
    private Long projectNo;
    @NotNull
    private Long authorNo;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
