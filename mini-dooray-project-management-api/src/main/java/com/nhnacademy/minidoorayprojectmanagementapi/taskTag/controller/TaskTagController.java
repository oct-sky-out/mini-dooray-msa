package com.nhnacademy.minidoorayprojectmanagementapi.taskTag.controller;

import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.dto.TaskTagBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.dto.TaskTagRegisterRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.dto.TaskTagRegisterRequestList;
import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.service.TaskTagService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("/projects/{projectNo}/tasks/tag")
public class TaskTagController {
    private final TaskTagService taskTagService;

    public TaskTagController(TaskTagService taskTagService) {
        this.taskTagService = taskTagService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> registerTagsToTask(@PathVariable("projectNo") Long projectNo,
                                                   @Validated @RequestBody
                                                   TaskTagRegisterRequestList taskTagRegisterRequests){
        List<TaskTagBasicDto> savedTaskTag = taskTagService.addTaskTag(projectNo, taskTagRegisterRequests);
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("result", savedTaskTag);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }
}
