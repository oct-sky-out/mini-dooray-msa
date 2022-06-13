package com.nhnacademy.minidoorayprojectmanagementapi.comment.service;

import com.nhnacademy.minidoorayprojectmanagementapi.comment.dto.CommentBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.comment.dto.CommentCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.comment.entity.Comment;
import com.nhnacademy.minidoorayprojectmanagementapi.comment.repository.CommentRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.exceptions.ProjectMemberNotFoundException;
import com.nhnacademy.minidoorayprojectmanagementapi.exceptions.TaskNotFoundException;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.repository.ProjectMemberRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.Task;
import com.nhnacademy.minidoorayprojectmanagementapi.task.repository.TaskRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    private final TaskRepository taskRepository;

    private final ProjectMemberRepository projectMemberRepository;

    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository,
                          ProjectMemberRepository projectMemberRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    @Transactional
    public CommentBasicDto registerComment(CommentCreationRequest commentCreationRequest){
        Long authorNo = commentCreationRequest.getAuthor();
        Long taskNo = commentCreationRequest.getTaskNo();

        Task task = taskRepository.findById(taskNo)
            .orElseThrow(TaskNotFoundException::new);
        ProjectMember.Pk memberPk =
            new ProjectMember.Pk(authorNo, task.getProject().getProjectNo());
        ProjectMember author = projectMemberRepository.findById(memberPk)
            .orElseThrow(ProjectMemberNotFoundException::new);
        ;

        Comment comment = Comment.builder()
            .content(commentCreationRequest.getContent())
            .task(task)
            .author(author.getPk().getUserNo())
            .createdAt(LocalDateTime.now())
            .build();

        Comment savedComment = commentRepository.saveAndFlush(comment);

        return new CommentBasicDto(
            savedComment.getCommentNo(),
            savedComment.getAuthor(),
            author.getId(),
            savedComment.getTask().getTaskNo(),
            savedComment.getTask().getTitle(),
            savedComment.getContent(),
            savedComment.getCreatedAt()
        );
    }
}
