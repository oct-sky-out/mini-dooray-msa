package com.nhnacademy.minidoorayprojectmanagementapi.project.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProjectBasicDto {

    private Long projectNo;

    private String name;

    private String adminId;

    private LocalDateTime createdAt;
}
