package com.nhnacademy.minidoorayprojectmanagementapi.tag.dto;

import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectExecutionCompleteDto;
import lombok.Data;

@Data
public class TagBasicDto {
    private Long tagNo;

    private ProjectExecutionCompleteDto project;

    private String name;
}
