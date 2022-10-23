package com.project.gym.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum LessonType {

    GENERAL("일반수업"),
    PERSONAL("PT수업");

    private String value;

    LessonType(String value){
        this.value = value;
    }


    @JsonCreator
    public static LessonType from(String value) {
        for (LessonType status : LessonType.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}
