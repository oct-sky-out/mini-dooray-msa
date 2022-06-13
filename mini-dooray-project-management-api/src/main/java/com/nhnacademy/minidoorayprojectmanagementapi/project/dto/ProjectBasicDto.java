package com.nhnacademy.minidoorayprojectmanagementapi.project.dto;

import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.ProjectStatus;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProjectBasicDto {

    private Long projectNo;

    private String name;

    private String adminId;

    private ProjectStatus status;

    private LocalDateTime createdAt;
}