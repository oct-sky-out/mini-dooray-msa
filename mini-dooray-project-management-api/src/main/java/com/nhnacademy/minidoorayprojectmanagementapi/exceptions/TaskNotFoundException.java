package com.nhnacademy.minidoorayprojectmanagementapi.exceptions;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException() {
        super("업무를 찾을 수 없습니다.");
    }
}
