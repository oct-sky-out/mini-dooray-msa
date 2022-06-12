package com.nhnacademy.minidoorayprojectmanagementapi.exceptions;

public class MilestoneNotFoundException extends RuntimeException{
    public MilestoneNotFoundException() {
        super("마일스톤을 찾을 수 없습니다.");
    }
}
