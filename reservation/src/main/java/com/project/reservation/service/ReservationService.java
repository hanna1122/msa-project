package com.project.reservation.service;

import com.project.reservation.domain.Reservation;
import com.project.reservation.domain.ReservationStatus;
import com.project.reservation.dto.ReservationDto;
import com.project.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    public final ReservationRepository reservationRepository;

    public Reservation saveReservation(ReservationDto reservationDto, String userId){

        return reservationRepository.save(new Reservation(reservationDto, userId));
    }

    public Reservation updateStatus(Long reservationId, ReservationStatus reservationStatus) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("예약번호가 존재하지 않습니다."));

        reservation.setReservationStatus(reservationStatus);
        return reservationRepository.save(reservation);
    }

    public List<ReservationDto> findReservations(String trainerId){
        return reservationRepository.findByTrainerId(trainerId)
                .stream()
                .map(ReservationDto::of)
                .collect(Collectors.toList());
    }
}
