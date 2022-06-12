package com.nhnacademy.minidoorayprojectmanagementapi.tag.entity;

import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
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
import lombok.Setter;
import org.hibernate.Hibernate;

@Entity
@Table(name = "tags")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_no", nullable = false)
    private Long tagNo;

    @JoinColumn(name = "project_no")
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @Column(name = "tag_name", length = 10)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null ||
            Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Tag tag = (Tag) o;
        return tagNo != null && Objects.equals(tagNo, tag.tagNo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
