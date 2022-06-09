package com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity;

import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Entity
@Table(name = "project_members")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProjectMember {
    @Id
    @Column(name = "user_no", nullable = false)
    private Long userNo;

    @MapsId
    @JoinColumn(name = "project_no")
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @Column(name = "id", length = 50)
    private String id;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null ||
            Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        ProjectMember that = (ProjectMember) o;
        return userNo != null && Objects.equals(userNo, that.userNo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
