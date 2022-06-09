package com.nhnacademy.minidoorayprojectmanagementapi.milestone.entity;

import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.Task;
import java.time.LocalDate;
import java.util.LinkedHashSet;
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

@Entity
@Table(name = "milestones")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MileStone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_no", nullable = false)
    private Long milestoneNo;

    @JoinColumn(name = "project_no", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @Column(name = "milestone_name", length = 20, nullable = false)
    private String name;

    @Column(name = "start_date", length = 20, nullable = false)
    private LocalDate start;

    @Column(name = "end_date", length = 20, nullable = false)
    private LocalDate end;

    @Column(name = "end_status", length = 20, nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean status;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "mile_stone_milestone_no")
    private Set<Task> tasks = new LinkedHashSet<>();

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
