package com.nhnacademy.minidoorayprojectmanagementapi.comment.entity;

import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.Task;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Entity
@Table(name = "comments")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no", nullable = false)
    private Long commentNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_no", nullable = false)
    private Task task;

    @Column(name = "comment_content", nullable = false)
    private String content;

    @Column(name = "comment_author_user_no", nullable = false)
    private Long author;

    @Column(name = "comment_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null ||
            Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Comment comment = (Comment) o;
        return commentNo != null && Objects.equals(commentNo, comment.commentNo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
