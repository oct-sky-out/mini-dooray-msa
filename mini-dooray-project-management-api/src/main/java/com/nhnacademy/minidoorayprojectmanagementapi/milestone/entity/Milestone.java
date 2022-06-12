package com.nhnacademy.minidoorayprojectmanagementapi.milestone.entity;

import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.Task;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Entity
@Table(name = "milestones")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Milestone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_no", nullable = false)
    private Long milestoneNo;

    @JoinColumn(name = "project_no", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @Column(name = "milestone_name", length = 30, nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDate start;

    @Column(name = "end_date", nullable = false)
    private LocalDate end;

    @Column(name = "end_status", nullable = false, columnDefinition = "BOOLEAN default 0")
    private Boolean endStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null ||
            Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Milestone milestone = (Milestone) o;
        return milestoneNo != null && Objects.equals(milestoneNo, milestone.milestoneNo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
