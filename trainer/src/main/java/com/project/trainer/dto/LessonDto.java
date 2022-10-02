package com.project.trainer.dto;

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

    private LocalDate startDate;

    private LocalDate endDate;

    public static LessonDto create(Lessons lessons){
        return LessonDto.builder()
                .trainerId(lessons.getTrainerId())
                .lessonName(lessons.getLessonName())
                .price(lessons.getPrice())
                .startDate(lessons.getStartDate())
                .endDate(lessons.getEndDate())
                .build();
    }
}
