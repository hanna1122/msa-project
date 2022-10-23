package com.project.gym.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AttendanceDto {

    private Long attendanceId;

    private Long ticketId;

    private String userId;

}
