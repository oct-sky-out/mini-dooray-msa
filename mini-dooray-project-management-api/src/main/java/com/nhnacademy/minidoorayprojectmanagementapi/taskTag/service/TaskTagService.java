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
import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.dto.TaskTagRequest;
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
        List<TaskTagRequest> taskTagRequests =
            taskTagRegisterRequestList.getTaskTagRequests();
        long taskNo = taskTagRequests.get(0).getTaskNo();
        validateHasDifferentTaskNo(taskTagRequests, taskNo);

        Task task = getTaskByTaskNo(taskNo);
        validProjectNo(projectNo, task);

        List<Tag> tags = getTags(taskTagRequests);
        List<TaskTag> taskTags = generateTaskTags(task, tags);

        return taskTagRepository.saveAllAndFlush(taskTags).stream()
            .map(taskTag -> new TaskTagBasicDto(
                taskTag.getPk().getTagNo(),
                taskTag.getTag().getName(),
                taskTag.getPk().getTaskNo(),
                taskTag.getTask().getTitle()))
            .collect(Collectors.toList());
    }

    @Transactional
    public String dropTaskTag(Long projectNo, TaskTagRegisterRequestList taskTagRegisterRequestList) {
        List<TaskTagRequest> taskTagRequests =
            taskTagRegisterRequestList.getTaskTagRequests();
        long taskNo = taskTagRequests.get(0).getTaskNo();
        validateHasDifferentTaskNo(taskTagRequests, taskNo);

        Task task = getTaskByTaskNo(taskNo);
        validProjectNo(projectNo, task);

        List<TaskTag.Pk> taskTagPks = taskTagRequests.stream()
            .map(taskTagRequest ->
                new TaskTag.Pk(taskTagRequest.getTaskNo(),taskTagRequest.getTagNo()))
            .collect(Collectors.toList());

        taskTagRepository.dropTaskTag(taskTagPks);

        return "success";
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

    private void validProjectNo(Long projectNo, Task task) {
        if(!Objects.equals(task.getProject().getProjectNo(), projectNo)){
            throw new DoseNotEqualProjectNo();
        }
    }

    private void validateHasDifferentTaskNo(List<TaskTagRequest> taskTagRequest, long taskNo) {
        boolean hasDifferentTaskNo = taskTagRequest.stream()
            .noneMatch(taskTagRegisterTarget -> Objects.equals(taskTagRegisterTarget.getTaskNo(), taskNo));

        if(hasDifferentTaskNo){
            throw new IncludedDifferentTaskNoException();
        }
    }

    private Task getTaskByTaskNo(long taskNo) {
        return taskRepository.findById(taskNo)
            .orElseThrow(TaskNotFoundException::new);
    }

    private List<Tag> getTags(List<TaskTagRequest> taskTagRequest) {
        return taskTagRequest.stream()
            .map(taskTagRegisterTarget ->
                tagRepository.findById(taskTagRegisterTarget.getTagNo())
                    .orElseThrow(TagNotFoundException::new))
            .collect(Collectors.toList());
    }

}
