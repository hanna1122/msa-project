package com.project.gym.domain;

import lombok.Getter;

@Getter
public enum UserType {

    GENERAL("일반회원"),
    PERSONAL("PT회원");

    private String message;

    UserType(String message){
        this.message = message;
    }
}
