package com.nhnacademy.minidoorayprojectmanagementapi.project.dto;

import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.ProjectStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectCreationCompleteDto {
    private Long projectNo;

    private String adminId;

    private String name;

    private ProjectStatus status;

    private LocalDateTime createdAt;
}
