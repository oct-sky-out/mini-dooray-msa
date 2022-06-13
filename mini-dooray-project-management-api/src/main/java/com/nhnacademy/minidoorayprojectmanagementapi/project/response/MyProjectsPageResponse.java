package com.nhnacademy.minidoorayprojectmanagementapi.project.response;

import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectBasicDto;
import java.util.List;
import lombok.Value;

@Value
public class MyProjectsPageResponse {
    List<ProjectBasicDto> projects;

    boolean hasNext;

    boolean hasPrevious;

    long currentPage;
}
