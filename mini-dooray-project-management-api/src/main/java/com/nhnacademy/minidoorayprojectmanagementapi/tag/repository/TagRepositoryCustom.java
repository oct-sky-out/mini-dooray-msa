package com.nhnacademy.minidoorayprojectmanagementapi.tag.repository;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TagRepositoryCustom {
    Long modifyTag(Long projectNo, Long tagNo, String tagName);
}
