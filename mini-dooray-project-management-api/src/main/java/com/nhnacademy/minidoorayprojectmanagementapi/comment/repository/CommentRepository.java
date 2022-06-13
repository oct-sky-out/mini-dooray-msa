package com.nhnacademy.minidoorayprojectmanagementapi.comment.repository;

import com.nhnacademy.minidoorayprojectmanagementapi.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
