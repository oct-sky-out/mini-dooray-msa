package com.nhnacademy.minidoorayuserapi.user.entity;

import lombok.ToString;

@ToString
public enum UserStatus {
    JOINED("JOINED"),
    LEAVED("LEAVED"),
    DORMANT("DORMANT");

    private String state;

    UserStatus(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
