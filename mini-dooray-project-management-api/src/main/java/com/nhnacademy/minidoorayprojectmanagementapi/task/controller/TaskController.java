package com.nhnacademy.minidoorayprojectmanagementapi.task.controller;

import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskDetailResponse;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskExecutionCompleteDto;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskModifyRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskPageResponse;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskRegisterMilestoneRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.task.service.TaskService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects/{projectNo}/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskExecutionCompleteDto createNewTask(@Validated @RequestBody TaskCreationRequest creationRequest){
        return taskService.createTask(creationRequest);
    }

    @PutMapping
    public TaskExecutionCompleteDto modifyTask(@Validated @RequestBody TaskModifyRequest modifyRequest){
        return taskService.modifyTask(modifyRequest);
    }

    @DeleteMapping("/{taskNo}")
    public TaskExecutionCompleteDto deleteTask(@PathVariable("taskNo") Long taskNo){
        return taskService.removeTask(taskNo);
    }

    @GetMapping
    public TaskPageResponse showTaskApi(@PathVariable("projectNo")Long projectNo, Pageable pageable){
        return taskService.getTaskPage(projectNo, pageable);
    }

    @GetMapping("/{taskNo}")
    public TaskDetailResponse showDetailTaskApi(@PathVariable("projectNo") Long projectNo,
                                                @PathVariable("taskNo") Long taskNo){
        return taskService.findDetailTask(projectNo, taskNo);
    }

    @PostMapping("/{taskNo}/milestone")
    public TaskExecutionCompleteDto registerMilestone(@PathVariable("projectNo") Long projectNo,
                                                      @PathVariable("taskNo") Long taskNo,
                                                      @Validated @RequestBody
                                                      TaskRegisterMilestoneRequest milestoneRequest) {
        return taskService.registerMilestoneToTask(projectNo, taskNo, milestoneRequest.getMilestoneNo());
    }

    @DeleteMapping("/{taskNo}/milestone")
    public TaskExecutionCompleteDto dropMilestone(@PathVariable("projectNo") Long projectNo,
                                                  @PathVariable("taskNo") Long taskNo) {
        return taskService.dropMilestone(projectNo, taskNo);
    }
}
