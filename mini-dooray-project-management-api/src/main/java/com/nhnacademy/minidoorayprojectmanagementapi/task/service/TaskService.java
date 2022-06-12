package com.nhnacademy.minidoorayprojectmanagementapi.task.service;

import com.nhnacademy.minidoorayprojectmanagementapi.exceptions.TaskCreateException;
import com.nhnacademy.minidoorayprojectmanagementapi.exceptions.TaskNotFoundException;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.entity.MileStone;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.repository.ProjectRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.repository.ProjectMemberRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskExecutionCompleteDto;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskModifyRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.Task;
import com.nhnacademy.minidoorayprojectmanagementapi.task.repository.TaskRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository,
                       ProjectMemberRepository projectMemberRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    public TaskExecutionCompleteDto createTask(TaskCreationRequest creationRequest) {
        Optional<Project> project = projectRepository.findById(creationRequest.getProjectNo());
        Optional<ProjectMember> member = projectMemberRepository.findById(
            new ProjectMember.Pk(creationRequest.getAuthorNo(), creationRequest.getProjectNo()));

        if (project.isPresent() && member.isPresent()) {
            Task task = Task.builder()
                .title(creationRequest.getTitle())
                .content(creationRequest.getContent())
                .project(project.get())
                .author(member.get().getPk().getUserNo())
                .createdAt(LocalDateTime.now())
                .build();
            Task savedTask = taskRepository.saveAndFlush(task);
            return TaskExecutionCompleteDto.builder()
                .projectNo(savedTask.getProject().getProjectNo())
                .author(savedTask.getAuthor())
                .title(savedTask.getTitle())
                .content(savedTask.getContent())
                .createdAt(savedTask.getCreatedAt())
                .build();
        }
        throw new TaskCreateException();
    }

    public TaskExecutionCompleteDto modifyTask(TaskModifyRequest taskModifyRequest) {
        Task task = taskRepository.findById(taskModifyRequest.getTaskNo())
            .orElseThrow(TaskNotFoundException::new);

        task.modifyTask(taskModifyRequest.getTitle(), taskModifyRequest.getContent());

        Optional<MileStone> mileStone = Optional.ofNullable(task.getMileStone());
        Long milestoneNo = mileStone.map(MileStone::getMilestoneNo).orElse(null);

        taskRepository.saveAndFlush(task);
        return TaskExecutionCompleteDto.builder()
            .taskNo(task.getTaskNo())
            .projectNo(task.getProject().getProjectNo())
            .title(task.getTitle())
            .content(task.getContent())
            .milestoneNo(milestoneNo)
            .author(task.getAuthor())
            .createdAt(task.getCreatedAt())
            .build();
    }
}
