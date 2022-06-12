package com.nhnacademy.minidoorayprojectmanagementapi.tag.repository;

import com.nhnacademy.minidoorayprojectmanagementapi.tag.entity.QTag;
import com.nhnacademy.minidoorayprojectmanagementapi.tag.entity.Tag;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class TagRepositoryImpl extends QuerydslRepositorySupport implements TagRepositoryCustom {
    public TagRepositoryImpl() {
        super(Tag.class);
    }

    @Override
    public Long modifyTag(Long projectNo, Long tagNo, String tagName) {
        QTag tag = QTag.tag;
        return update(tag)
            .where(tag.tagNo.eq(tagNo).and(tag.project.projectNo.eq(projectNo)))
            .set(tag.name, tagName)
            .execute();
    }
}
