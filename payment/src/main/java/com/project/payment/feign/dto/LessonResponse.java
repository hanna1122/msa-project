package com.project.payment.feign.dto;



import lombok.Getter;

import java.time.LocalDate;

@Getter
public class LessonResponse {
    private Long id;

    private String trainerId;

    private String lessonName;

    private Long price;

    private LocalDate startDate;

    private LocalDate endDate;
}
