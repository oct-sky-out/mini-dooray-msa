package com.nhnacademy.minidoorayprojectmanagementapi.task.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TaskModifyRequest {
    @NotNull
    private Long taskNo;

    @NotBlank
    @Length(max = 30)
    private String title;

    @NotBlank
    private String content;
}
