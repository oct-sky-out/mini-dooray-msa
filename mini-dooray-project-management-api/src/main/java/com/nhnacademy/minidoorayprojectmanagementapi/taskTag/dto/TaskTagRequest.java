package com.nhnacademy.minidoorayprojectmanagementapi.taskTag.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskTagRequest {
    @NotNull
    Long tagNo;

    @NotNull
    Long taskNo;
}
