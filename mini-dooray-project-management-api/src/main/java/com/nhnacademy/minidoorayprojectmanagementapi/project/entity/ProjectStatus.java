package com.nhnacademy.minidoorayprojectmanagementapi.project.entity;

public enum ProjectStatus {
    ACTIVE("ACTIVE"),
    DORMANT("DORMANT"),
    END("END");

    private final String status;

    ProjectStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
