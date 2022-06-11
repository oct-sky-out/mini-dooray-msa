package com.nhnacademy.minidoorayprojectmanagementapi.task.dto;

import com.nhnacademy.minidoorayprojectmanagementapi.milestone.entity.MileStone;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskExecutionCompleteDto {
    private Long taskNo;

    private Long projectNo;

    private Long author;

    private Long milestoneNo;

    private String title;

    private String content;

    private LocalDateTime createdAt;
}
