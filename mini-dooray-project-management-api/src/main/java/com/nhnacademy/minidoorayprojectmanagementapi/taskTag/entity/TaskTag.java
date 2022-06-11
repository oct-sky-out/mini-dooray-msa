package com.nhnacademy.minidoorayprojectmanagementapi.taskTag.entity;

import com.nhnacademy.minidoorayprojectmanagementapi.tag.entity.Tag;
import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.Task;
import java.io.Serializable;
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

@Entity
@Table(name = "task_tags")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TaskTag {
    @EmbeddedId
    private Pk pk;

    @MapsId("tagNo")
    @JoinColumn(name = "tag_no")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

    @MapsId("taskNo")
    @JoinColumn(name = "task_no")
    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Pk implements Serializable {
        @Column(name = "task_no", nullable = false)
        private Long taskNo;

        @Column(name = "tag_no", nullable = false)
        private Long tagNo;
    }
}
