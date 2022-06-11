package com.nhnacademy.minidoorayprojectmanagementapi.project.entity;

import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;

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

    @JoinColumn(name = "project_admin_no")
    @OneToOne(fetch = FetchType.LAZY)
    private ProjectMember projectMember;

    @Column(name = "project_name", length = 30)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_status")
    @ColumnDefault("ACTIVE")
    private ProjectStatus status;

    @Column(name = "project_create_at")
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
        Project project = (Project) o;
        return projectNo != null && Objects.equals(projectNo, project.projectNo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
