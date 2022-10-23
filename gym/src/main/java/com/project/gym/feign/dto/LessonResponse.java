package com.project.gym.feign.dto;

import com.project.gym.domain.LessonType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@Getter
@ToString
public class LessonResponse {
    private Long id;

    private String trainerId;

    private String lessonName;

    private Long price;

    private Long count;

    private LessonType lessonType;

    private LocalDate startDate;

    private LocalDate endDate;
}