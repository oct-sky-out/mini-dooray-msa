package com.nhnacademy.minidoorayprojectmanagementapi.task.entity;

import com.nhnacademy.minidoorayprojectmanagementapi.milestone.entity.Milestone;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Entity
@Table(name = "tasks")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_no", nullable = false)
    private Long taskNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_no", nullable = false)
    private Project project;

    @Column(name = "task_author_user_no")
    private Long author;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_no")
    private Milestone mileStone;

    @Column(name = "task_title", length = 30, nullable = false)
    private String title;

    @Column(name = "task_content", nullable = false)
    private String content;

    @Column(name = "task_created_at", nullable = false)
    private LocalDateTime createdAt;

    public void modifyTask(String title, String content){
        this.title = title;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null ||
            Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Task task = (Task) o;
        return taskNo != null && Objects.equals(taskNo, task.taskNo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
