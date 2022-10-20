package com.project.reservation.feign.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketRequest {
    private Long id;
    private String reservationStatus;
}
