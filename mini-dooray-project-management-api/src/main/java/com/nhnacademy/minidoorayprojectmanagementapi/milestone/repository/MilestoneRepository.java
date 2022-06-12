package com.nhnacademy.minidoorayprojectmanagementapi.milestone.repository;

import com.nhnacademy.minidoorayprojectmanagementapi.milestone.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
}
