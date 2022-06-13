package com.nhnacademy.minidoorayprojectmanagementapi.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.ProjectStatus;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProjectBasicDto {

    private Long projectNo;

    private String name;

    private String adminId;

    private ProjectStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
