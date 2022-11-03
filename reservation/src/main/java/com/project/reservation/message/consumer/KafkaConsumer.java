package com.project.reservation.message.consumer;


import com.project.reservation.domain.Reservation;
import com.project.reservation.domain.ReservationStatus;
import com.project.reservation.message.event.ReservationRollbackEvent;
import com.project.reservation.repository.ReservationRepository;
import com.project.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumer {
    public final ReservationRepository reservationRepository;
    public final ReservationService reservationService;

    @KafkaListener(topics = "reservation-rollback-topic", groupId = "myGroup")
    public void consume(ReservationRollbackEvent event) throws IOException {
        log.info("reservationRollback rollbackGroup 이벤트 수신");
        log.info("[reservationId : {}] is updated.", event.getReservationId());

        Reservation reservation =  reservationService.getReservation(event.getReservationId());
        reservation.setReservationStatus(ReservationStatus.FAILED);
        reservationRepository.save(reservation);
    }
}