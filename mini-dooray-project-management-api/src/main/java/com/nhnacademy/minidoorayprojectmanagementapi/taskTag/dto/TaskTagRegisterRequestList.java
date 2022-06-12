package com.nhnacademy.minidoorayprojectmanagementapi.taskTag.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskTagRegisterRequestList {
    @Valid
    @NotNull
    @Size(min = 1)
    private List<TaskTagRequest> taskTagRequests;
}
