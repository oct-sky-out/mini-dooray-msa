package com.nhnacademy.minidoorayprojectmanagementapi.taskTag.service;

import com.nhnacademy.minidoorayprojectmanagementapi.exceptions.DoseNotEqualProjectNo;
import com.nhnacademy.minidoorayprojectmanagementapi.exceptions.IncludedDifferentTaskNoException;
import com.nhnacademy.minidoorayprojectmanagementapi.exceptions.TagNotFoundException;
import com.nhnacademy.minidoorayprojectmanagementapi.exceptions.TaskNotFoundException;
import com.nhnacademy.minidoorayprojectmanagementapi.tag.entity.Tag;
import com.nhnacademy.minidoorayprojectmanagementapi.tag.repository.TagRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.Task;
import com.nhnacademy.minidoorayprojectmanagementapi.task.repository.TaskRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.dto.TaskTagBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.dto.TaskTagRegisterRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.dto.TaskTagRegisterRequestList;
import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.entity.TaskTag;
import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.repository.TaskTagRepository;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskTagService {
    private final TaskTagRepository taskTagRepository;
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;

    public TaskTagService(TaskTagRepository taskTagRepository, TaskRepository taskRepository,
                          TagRepository tagRepository) {
        this.taskTagRepository = taskTagRepository;
        this.taskRepository = taskRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional
    public List<TaskTagBasicDto> addTaskTag(Long projectNo, TaskTagRegisterRequestList taskTagRegisterRequestList){
        List<TaskTagRegisterRequest> taskTagRegisterRequests =
            taskTagRegisterRequestList.getTaskTagRegisterRequests();
        long taskNo = taskTagRegisterRequests.get(0).getTaskNo();
        validateHasDifferentTaskNo(taskTagRegisterRequests, taskNo);

        Task task = getTaskByTaskNo(taskNo);
        if(!Objects.equals(task.getProject().getProjectNo(), projectNo)){
            throw new DoseNotEqualProjectNo();
        }

        List<Tag> tags = getTags(taskTagRegisterRequests);
        List<TaskTag> taskTags = generateTaskTags(task, tags);

        return taskTagRepository.saveAllAndFlush(taskTags).stream()
            .map(taskTag -> new TaskTagBasicDto(
                taskTag.getPk().getTagNo(),
                taskTag.getTag().getName(),
                taskTag.getPk().getTaskNo(),
                taskTag.getTask().getTitle()))
            .collect(Collectors.toList());
    }

    private List<TaskTag> generateTaskTags(Task task, List<Tag> tags) {
        AtomicInteger index = new AtomicInteger(0);

        return tags.stream()
            .map(tag -> new TaskTag.Pk(task.getTaskNo(), tag.getTagNo()))
            .map(pk -> TaskTag.builder()
                .task(task)
                .tag(tags.get(index.getAndIncrement()))
                .pk(pk)
                .build())
            .collect(Collectors.toList());
    }

    private void validateHasDifferentTaskNo(List<TaskTagRegisterRequest> taskTagRegisterRequest, long taskNo) {
        boolean hasDifferentTaskNo = taskTagRegisterRequest.stream()
            .noneMatch(taskTagRegisterTarget -> Objects.equals(taskTagRegisterTarget.getTaskNo(), taskNo));

        if(hasDifferentTaskNo){
            throw new IncludedDifferentTaskNoException();
        }
    }

    private Task getTaskByTaskNo(long taskNo) {
        return taskRepository.findById(taskNo)
            .orElseThrow(TaskNotFoundException::new);
    }

    private List<Tag> getTags(List<TaskTagRegisterRequest> taskTagRegisterRequest) {
        return taskTagRegisterRequest.stream()
            .map(taskTagRegisterTarget ->
                tagRepository.findById(taskTagRegisterTarget.getTagNo())
                    .orElseThrow(TagNotFoundException::new))
            .collect(Collectors.toList());
    }
}
