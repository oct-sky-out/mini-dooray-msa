package com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity;

import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

@Entity
@Table(name = "project_members")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProjectMember {
    @EmbeddedId
    public Pk pk;

    @MapsId("projectNo")
    @JoinColumn(name = "project_no")
    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private Project project;

    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "project_admin", columnDefinition = "BOOLEAN default 0")
    private Boolean isAdmin;

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Setter
    public static class Pk implements Serializable {
        @Column(name = "user_no")
        private Long userNo;

        @Column(name = "project_no")
        private Long projectNo;
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
        ProjectMember that = (ProjectMember) o;
        return pk != null && Objects.equals(pk, that.pk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk);
    }
}
