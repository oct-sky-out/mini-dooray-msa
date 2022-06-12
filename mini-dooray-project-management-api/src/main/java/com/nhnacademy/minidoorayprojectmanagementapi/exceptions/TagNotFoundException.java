package com.nhnacademy.minidoorayprojectmanagementapi.exceptions;

public class TagNotFoundException extends RuntimeException{
    public TagNotFoundException() {
        super("태그를 찾을 수 없습니다.");
    }
}
