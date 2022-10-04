package com.project.trainer.dto;

import com.project.trainer.domain.LessonType;
import com.project.trainer.domain.Lessons;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonDto {
    private Long id;

    private String trainerId;

    private String lessonName;

    private Long price;

    private Long count;

    private LessonType lessonType;

    private LocalDate startDate;

    private LocalDate endDate;

    public static LessonDto create(Lessons lessons){
        return LessonDto.builder()
                .trainerId(lessons.getTrainerId())
                .lessonName(lessons.getLessonName())
                .price(lessons.getPrice())
                .count(lessons.getCount())
                .lessonType(lessons.getLessonType())
                .startDate(lessons.getStartDate())
                .endDate(lessons.getEndDate())
                .build();
    }
}