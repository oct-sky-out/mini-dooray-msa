package com.nhnacademy.minidoorayprojectmanagementapi.task.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskPageResponse {
    private List<TaskPageDto> content;

    private Boolean hasNextPage;

    private Boolean hasPreviousPage;

    private Integer currentPage;
}
