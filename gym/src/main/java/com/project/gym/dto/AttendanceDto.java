package com.project.gym.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class AttendanceDto {

    private String userId;

    private Long orderId;

    private Long lessonId;

    private Long count;


    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime regDate;
}
