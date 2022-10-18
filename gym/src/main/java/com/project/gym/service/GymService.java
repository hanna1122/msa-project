package com.project.gym.service;

import com.project.gym.domain.Attendance;
import com.project.gym.domain.Ticket;
import com.project.gym.dto.TicketDto;
import com.project.gym.feign.client.TrainerServiceClient;
import com.project.gym.feign.client.UserServiceClient;
import com.project.gym.feign.dto.LessonResponse;
import com.project.gym.feign.dto.OrderRequest;
import com.project.gym.message.event.UserTypeUpdatedEvent;
import com.project.gym.repository.AttendanceRepository;
import com.project.gym.repository.AttendanceRepositoryCustom;
import com.project.gym.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GymService {
    private final TicketRepository ticketRepository;
    private final AttendanceRepository attendanceRepository;

    private final TrainerServiceClient trainerServiceClient;

    private final UserServiceClient userServiceClient;

    private final KafkaTemplate<String, UserTypeUpdatedEvent> kafkaTemplate;


    public Ticket saveTicket(OrderRequest orderRequest, String userId){
        LessonResponse lessonResponse = trainerServiceClient.getLesson(orderRequest.getLessonId());
        Ticket saveTicket;
        if(lessonResponse.getLessonType().equals("GENERAL")){
            TicketDto generalDto = TicketDto.generalTicket(orderRequest, lessonResponse);
            saveTicket = Ticket.generalTicket(generalDto);
        }else{
            TicketDto personalDto = TicketDto.personalTicket(orderRequest, lessonResponse);
            saveTicket = Ticket.personalTicket(personalDto);
        }

        UserTypeUpdatedEvent event = new UserTypeUpdatedEvent(userId, saveTicket.getType());

        log.info("userType-updated 이벤트 발신 : {} ", event);
        kafkaTemplate.send("userType-updated-topic", event);
        return ticketRepository.save(saveTicket);
    }

    public List<TicketDto> getTickets(String userId){
        return ticketRepository.findByUserId(userId)
                .stream()
                .map(TicketDto::of)
                .collect(Collectors.toList());
    }

    public TicketDto getTicket(Long ticketId){
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(()-> new RuntimeException("ticketId에 해당하는 이용권 없음"));

        return TicketDto.of(ticket);
    }

    public Attendance saveAttendance(String userId){


        Attendance attendance = Attendance.builder()
                .userId(userId)
                .build();
        return attendanceRepository.save(attendance);
    }

    public Ticket updateCount(TicketDto ticketDto, String userId){
        TicketDto updateCount = getTicket(ticketDto.getId());
        Long count = updateCount.getCount() - 1;
        updateCount.setCount(count);

        return ticketRepository.save(Ticket.personalTicket(updateCount));
    }
}
