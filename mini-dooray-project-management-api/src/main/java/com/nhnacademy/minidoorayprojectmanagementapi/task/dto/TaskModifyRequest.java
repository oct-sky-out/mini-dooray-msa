package com.nhnacademy.minidoorayprojectmanagementapi.task.dto;

import lombok.Data;

@Data
public class TaskModifyRequest {
    private Long taskNo;

    private String title;

    private String content;
}
