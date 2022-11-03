package com.project.gym.service;

import com.project.gym.domain.*;
import com.project.gym.dto.AttendanceDto;
import com.project.gym.dto.TicketDto;
import com.project.gym.feign.client.TrainerServiceClient;
import com.project.gym.feign.client.UserServiceClient;
import com.project.gym.feign.dto.LessonResponse;
import com.project.gym.feign.dto.OrderRequest;
import com.project.gym.feign.dto.UserResponse;
import com.project.gym.message.event.PaymentRollbackEvent;
import com.project.gym.message.event.ReservationRollbackEvent;
import com.project.gym.message.event.UserTypeUpdatedEvent;
import com.project.gym.repository.AttendanceRepository;
import com.project.gym.repository.AttendanceRepositoryCustom;
import com.project.gym.repository.TicketRepository;
//import kafka.Kafka;
import com.project.gym.repository.TicketRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GymService {
    private final TicketRepository ticketRepository;

    private final TicketRepositoryCustom ticketRepositoryCustom;

    private final AttendanceRepository attendanceRepository;

    private final AttendanceRepositoryCustom attendanceRepositoryCustom;

    private final TrainerServiceClient trainerServiceClient;

    private final UserServiceClient userServiceClient;

    private final KafkaTemplate<String, UserTypeUpdatedEvent> kafkaUserTypeTemplate;

    private final KafkaTemplate<String, PaymentRollbackEvent> kafkaRollbackTemplate;

    private final KafkaTemplate<String, ReservationRollbackEvent> kafkaReservationTemplate;


    public Ticket saveTicket(OrderRequest orderRequest, String userId){
        try{
            LessonResponse lessonResponse = trainerServiceClient.getLesson(orderRequest.getLessonId());
            Ticket saveTicket;
            if(lessonResponse.getLessonType().equals(LessonType.GENERAL)){
                TicketDto generalDto = TicketDto.generalTicket(orderRequest, lessonResponse);
                saveTicket = Ticket.generalTicket(generalDto);
            }else{
                TicketDto personalDto = TicketDto.personalTicket(orderRequest, lessonResponse);
                saveTicket = Ticket.personalTicket(personalDto);
            }

            UserTypeUpdatedEvent event = new UserTypeUpdatedEvent(userId, saveTicket.getType());

            log.info("userType-updated 이벤트 발신 : {} ", event);
            kafkaUserTypeTemplate.send("userType-updated-topic", event);

            return ticketRepository.save(saveTicket);
        } catch (Exception e) {
            PaymentRollbackEvent event = new PaymentRollbackEvent(orderRequest.getPaymentId(), orderRequest.getId());

            log.info("payment-rollback 이벤트 발신 : {} ", event);
            kafkaRollbackTemplate.send("payment-rollback-topic", event);
            return null;
        }

    }

    public List<TicketDto> getTickets(String userId){
        return ticketRepository.findByUserId(userId)
                .stream()
                .map(TicketDto::of)
                .collect(Collectors.toList());
    }

    public TicketDto getTicket(Long ticketId){
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(()-> new RuntimeException("ticket not found"));

        return TicketDto.of(ticket);
    }

    public Attendance saveAttendance(String userId){
        UserResponse user = userServiceClient.getUser(userId);
        AttendanceDto attendanceDto;
        if(user.getUserType().equals("GENERAL")){
            attendanceDto = attendanceRepositoryCustom.findGeneralAttendance(userId)
                    .orElseThrow(() -> new RuntimeException("General ticket not found"));
        }else{
            attendanceDto = attendanceRepositoryCustom.findPersonalAttendance(userId)
                    .orElseThrow(() -> new RuntimeException("Personal ticket not found"));
        }

        if(attendanceDto.getAttendanceId() != null){
            throw new RuntimeException("attendance already exists");
        }

        Attendance attendance = Attendance.builder()
                .userId(userId)
                .build();
        return attendanceRepository.save(attendance);
    }

    public void updateCount(Long ticketId, Long reservationId, String reservationStatus, String userId){
        try {
            Ticket findTicket = ticketRepositoryCustom.findTicket(ticketId);
            Long count = findTicket.getPersonalUser().getCount();
            if (reservationStatus.equals("CANCEL")) {
                count+=1;
            }else{
                count-=1;
            }
            ticketRepositoryCustom.updateCount(ticketId, count);

        } catch(Exception e) {
            ReservationRollbackEvent event = new ReservationRollbackEvent(reservationId);
            log.info("reservation-rollback 이벤트 발신 : {} ", event);
            kafkaReservationTemplate.send("reservation-rollback-topic", event);
        }
    }
}
