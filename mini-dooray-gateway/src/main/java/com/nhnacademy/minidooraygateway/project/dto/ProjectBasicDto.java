package com.nhnacademy.minidooraygateway.project.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProjectBasicDto {

    private Long projectNo;

    private String name;

    private LocalDateTime createdAt;
}
