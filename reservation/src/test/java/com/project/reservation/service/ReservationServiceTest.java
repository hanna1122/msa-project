package com.project.reservation.service;

import com.project.reservation.domain.Reservation;
import com.project.reservation.domain.ReservationStatus;
import com.project.reservation.dto.ReservationDto;
import com.project.reservation.exception.CustomException;
import com.project.reservation.feign.client.GymServiceClient;
import com.project.reservation.feign.dto.TicketResponse;
import com.project.reservation.feign.dto.UserResponse;
import com.project.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ReservationServiceTest {

    @Autowired
    private ReservationRepository reservationRepository;


    @Test
    public void reservationSaveTest(){
        String userId = "user12";
        String userName = "test";
        Long ticketId = 1L;
        Long lessonId = 1L;
        Long trainerId = 1L;
        LocalDate date = LocalDate.now();

        ReservationDto reservationDto = ReservationDto.builder()
                .ticketId(ticketId)
                .trainerId(trainerId)
                .lessonId(lessonId)
                .reservationDate(date)
                .build();

        UserResponse userResponse = UserResponse.builder()
                .userId(userId)
                .name(userName)
                .build();


        Reservation result = reservationRepository.save(new Reservation(reservationDto, userResponse));

        assertEquals(result.getReservatorId(), userId);
        assertEquals(result.getTicketId(), ticketId);
        assertEquals(result.getLessonId(), lessonId);
        assertEquals(result.getTrainerId(), trainerId);
        assertEquals(result.getReservatorName(), userName);
        assertEquals(result.getReservationDate(), date);
        assertEquals(result.getReservationStatus(), ReservationStatus.PLACED);
    }

    @Test
    public void getReservationTest(){
        Long reservationId = 1L;
        String userId = "user12";
        String userName = "test";
        Long ticketId = 1L;
        Long lessonId = 1L;
        Long trainerId = 1L;
        LocalDate date = LocalDate.now();

        Reservation result =  reservationRepository.findById(reservationId)
                .orElseThrow(()-> new CustomException(HttpStatus.CONFLICT, "예약번호가 존재하지 않습니다."));

        assertEquals(result.getReservatorId(), userId);
        assertEquals(result.getTicketId(), ticketId);
        assertEquals(result.getLessonId(), lessonId);
        assertEquals(result.getTrainerId(), trainerId);
        assertEquals(result.getReservatorName(), userName);
        assertEquals(result.getReservationDate(), date);
        assertEquals(result.getReservationStatus(), ReservationStatus.PLACED);
    }

    @Test
    public void updateStatusTest(){
        Long reservationId = 1L;
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(()-> new CustomException(HttpStatus.CONFLICT, "예약번호가 존재하지 않습니다."));

        reservation.setReservationStatus(ReservationStatus.FINISHED);

        Reservation result = reservationRepository.save(reservation);

        assertEquals(result.getReservationStatus(), ReservationStatus.FINISHED);
    }

    @Test
    public void findReservationsTest(){
        Long trainerId = 1L;
        List<ReservationDto> result =  reservationRepository.findByTrainerId(trainerId)
                .stream()
                .map(ReservationDto::of)
                .collect(Collectors.toList());

    }
}
