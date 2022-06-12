package com.nhnacademy.minidoorayprojectmanagementapi.taskTag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskTagBasicDto {
    private Long tagNo;

    private String tagName;

    private Long taskNo;

    private String taskName;
}
