package com.nhnacademy.minidoorayprojectmanagementapi.comment.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayprojectmanagementapi.comment.entity.Comment;
import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.Task;
import com.nhnacademy.minidoorayprojectmanagementapi.task.repository.TaskRepository;
import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TaskRepository taskRepository;

    @Test
    void commentRegisterTest() {
        Task task = taskRepository.findById(8L).get();

        Comment comment = Comment.builder()
            .task(task)
            .content("content!!")
            .createdAt(LocalDateTime.now())
            .author(100L)
            .build();

        Comment savedComment = commentRepository.saveAndFlush(comment);
        assertThat(savedComment.getContent()).isEqualTo(comment.getContent());
        assertThat(savedComment.getTask()).isEqualTo(comment.getTask());
        assertThat(savedComment.getCreatedAt()).isEqualTo(comment.getCreatedAt());
        assertThat(savedComment.getAuthor()).isEqualTo(comment.getAuthor());
    }
}
