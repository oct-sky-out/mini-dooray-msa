package com.nhnacademy.minidoorayprojectmanagementapi.exceptions;

public class TagUpdateFailureException extends RuntimeException {
    public TagUpdateFailureException() {
        super("태그 수정에 실패했습니다.");
    }
}
