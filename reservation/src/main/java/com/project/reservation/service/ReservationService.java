package com.project.reservation.service;

import com.project.reservation.domain.Reservation;
import com.project.reservation.domain.ReservationStatus;
import com.project.reservation.dto.ReservationDto;
import com.project.reservation.dto.Result;
import com.project.reservation.exception.CustomException;
import com.project.reservation.feign.client.GymServiceClient;
import com.project.reservation.feign.client.UserServiceClient;
import com.project.reservation.feign.dto.TicketResponse;
import com.project.reservation.feign.dto.UserResponse;
import com.project.reservation.message.event.CountUpdatedEvent;
import com.project.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    public final ReservationRepository reservationRepository;
    private final GymServiceClient gymServiceClient;
    private final UserServiceClient userServiceClient;

    private final KafkaTemplate<String, CountUpdatedEvent> kafkaTemplate;

    public Reservation saveReservation(ReservationDto reservationDto, String userId){
        Result<TicketResponse> ticketResponseResult = gymServiceClient.getTicket(reservationDto.getTicketId(), userId);
        if (Result.Code.ERROR.equals(ticketResponseResult.getCode())) {
            // error
            return null;
        }
        TicketResponse ticketResponse = ticketResponseResult.getData();

        if (!userId.equals(ticketResponse.getUserId())) {
            throw new CustomException(HttpStatus.CONFLICT, "이용권의 사용자 아이디와 다릅니다.");
        }

        if (ticketResponse.getCount() <= 0) {
            throw new CustomException(HttpStatus.CONFLICT, "이용권이 만료되었습니다.");
        }

        Result<UserResponse> userResponseResult = userServiceClient.getUser(userId);
        if (Result.Code.ERROR.equals(userResponseResult.getCode())) {
            // error
            return null;
        }
        UserResponse userResponse = userResponseResult.getData();

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
                .orElseThrow(()-> new CustomException(HttpStatus.CONFLICT, "예약번호가 존재하지 않습니다."));
    }

    public Reservation confirmReservation(Long reservationId, String userId) {
        Reservation reservation =  getReservation(reservationId);
        reservation.setReservationStatus(ReservationStatus.FINISHED);

        Reservation updatedReservation = reservationRepository.save(reservation);

        CountUpdatedEvent event = CountUpdatedEvent.builder()
                .id(updatedReservation.getTicketId())
                .reservationStatus("FINISHED")
                .reservationId(updatedReservation.getId())
                .build();

        log.info("count-updated 이벤트 발신 : {} ", event);
        kafkaTemplate.send("count-updated-topic", event);

        return reservation;
    }

    public Reservation cancelReservation(Long reservationId, String userId) {
        Reservation reservation =  getReservation(reservationId);
        reservation.setReservationStatus(ReservationStatus.CANCEL);

        Reservation updatedReservation = reservationRepository.save(reservation);

        CountUpdatedEvent event = CountUpdatedEvent.builder()
                .id(updatedReservation.getTicketId())
                .reservationId(updatedReservation.getId())
                .reservationStatus("CANCEL")
                .build();

        log.info("count-updated 이벤트 발신 : {} ", event);
        kafkaTemplate.send("count-updated-topic", event);

        return reservation;
    }
}
