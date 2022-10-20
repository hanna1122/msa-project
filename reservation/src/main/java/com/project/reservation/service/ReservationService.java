package com.project.reservation.service;

import com.project.reservation.domain.Reservation;
import com.project.reservation.domain.ReservationStatus;
import com.project.reservation.dto.ReservationDto;
import com.project.reservation.feign.client.GymServiceClient;
import com.project.reservation.feign.client.UserServiceClient;
import com.project.reservation.feign.dto.TicketRequest;
import com.project.reservation.feign.dto.TicketResponse;
import com.project.reservation.feign.dto.UserResponse;
import com.project.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    public final ReservationRepository reservationRepository;
    private final GymServiceClient gymServiceClient;
    private final UserServiceClient userServiceClient;

    public Reservation saveReservation(ReservationDto reservationDto, String userId){
        TicketResponse ticketResponse = gymServiceClient.getTicket(reservationDto.getTicketId(), userId);

        if (!userId.equals(ticketResponse.getUserId())) {
            throw new RuntimeException("이용권의 사용자 아이디와 다릅니다.");
        }

        if (ticketResponse.getCount() <= 0) {
            throw new RuntimeException("이용권이 만료되었습니다.");
        }

        UserResponse userResponse = userServiceClient.getUser(userId);

        return reservationRepository.save(new Reservation(reservationDto, userResponse));
    }

    public Reservation updateStatus(Long reservationId, ReservationStatus reservationStatus) {
        Reservation reservation =  getReservation(reservationId);

        reservation.setReservationStatus(reservationStatus);
        return reservationRepository.save(reservation);
    }

    public List<ReservationDto> findReservations(Long trainerId){
        return reservationRepository.findByTrainerId(trainerId)
                .stream()
                .map(ReservationDto::of)
                .collect(Collectors.toList());
    }

    public Reservation getReservation(Long reservationId){
        return reservationRepository.findById(reservationId)
                .orElseThrow(()-> new RuntimeException("예약번호가 존재하지 않습니다."));
    }

    public Reservation confirmReservation(Long reservationId, String userId) {
        Reservation reservation =  getReservation(reservationId);
        reservation.setReservationStatus(ReservationStatus.FINISHED);

        Reservation updatedReservation = reservationRepository.save(reservation);

        TicketRequest ticketRequest = TicketRequest.builder()
                .id(updatedReservation.getTicketId())
                .reservationStatus("FINISHED")
                .build();
        gymServiceClient.updateCount(ticketRequest, userId);

        return reservation;
    }

    public Reservation cancelReservation(Long reservationId, String userId) {
        Reservation reservation =  getReservation(reservationId);
        reservation.setReservationStatus(ReservationStatus.CANCEL);

        Reservation updatedReservation = reservationRepository.save(reservation);

        TicketRequest ticketRequest = TicketRequest.builder()
                .id(updatedReservation.getTicketId())
                .reservationStatus("CANCEL")
                .build();
        gymServiceClient.updateCount(ticketRequest, userId);

        return reservation;
    }
}
