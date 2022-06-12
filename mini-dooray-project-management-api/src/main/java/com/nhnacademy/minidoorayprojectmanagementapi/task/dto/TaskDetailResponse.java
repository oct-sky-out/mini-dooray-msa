package com.nhnacademy.minidoorayprojectmanagementapi.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.dto.MilestoneBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectExecutionCompleteDto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TaskDetailResponse {
    private Long taskNo;

    private ProjectExecutionCompleteDto project;

    private MilestoneBasicDto milestone;

    private String author;

    private String title;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
