package com.project.reservation.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.reservation.dto.ReservationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Table(name = "RESERVATIONS")
@EntityListeners(AuditingEntityListener.class)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reservatorId;

    private Long ticketId;

    private Long trainerId;

    private Long lessonId;

    @Column(length = 20, nullable = false)
    private String reservatorName;

    private LocalDate reservationDate;

    private String reservationTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @Column(nullable = false, updatable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime regDate;

    public Reservation(ReservationDto dto, String userId){
        this.ticketId = dto.getTicketId();
        this.lessonId = dto.getLessonId();
        this.reservatorId = userId;
        this.reservatorName = dto.getReservatorName();
        this.reservationDate = dto.getReservationDate();
        this.reservationTime = dto.getReservationTime();
        this.reservationStatus = ReservationStatus.PLACED;
    }

    public void setReservationStatus(ReservationStatus reservationStatus){
        this.reservationStatus = reservationStatus;
    }
}
