package com.project.reservation.dto;

import com.project.reservation.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long id;

    private Long ticketId;

    private String reservatorId;

    private Long trainerId;

    private Long lessonId;

    private String reservatorName;

    private LocalDate reservationDate;

    private String reservationTime;

    public static ReservationDto of(Reservation reservations){
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.id = reservations.getId();
        reservationDto.trainerId = reservations.getTrainerId();
        reservationDto.ticketId = reservations.getTicketId();
        reservationDto.reservatorId = reservations.getReservatorId();
        reservationDto.lessonId = reservations.getLessonId();
        reservationDto.reservatorName = reservations.getReservatorName();
        reservationDto.reservationDate = reservations.getReservationDate();
        reservationDto.reservationTime = reservations.getReservationTime();
        return reservationDto;
    }
}
