package com.project.gym.feign.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class LessonResponse {
    private Long id;

    private String trainerId;

    private String lessonName;

    private Long price;

    private Long count;

    private String lessonType;

    private LocalDate startDate;

    private LocalDate endDate;
}