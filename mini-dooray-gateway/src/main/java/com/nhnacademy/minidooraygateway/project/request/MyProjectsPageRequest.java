package com.nhnacademy.minidooraygateway.project.request;

import com.nhnacademy.minidooraygateway.project.dto.ProjectBasicDto;
import java.util.List;
import lombok.Data;

@Data
public class MyProjectsPageRequest {
    List<ProjectBasicDto> projects;

    boolean hasNext;

    boolean hasPrevious;

    long currentPage;
}
