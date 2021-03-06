package com.nhnacademy.minidoorayprojectmanagementapi.project.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Entity
@Table(name = "projects")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_no")
    private Long projectNo;

    @Column(name = "project_name", length = 30)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_status")
    private ProjectStatus status = ProjectStatus.ACTIVE;

    @Column(name = "project_create_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null ||
            Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Project project = (Project) o;
        return projectNo != null && Objects.equals(projectNo, project.projectNo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
