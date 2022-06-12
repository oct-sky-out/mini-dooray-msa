package com.nhnacademy.minidoorayprojectmanagementapi.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TaskPageDto {
    private Long taskNo;

    private Long projectNo;

    private Long author;

    private Long milestoneNo;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
