package com.project.gym.service;

import com.project.gym.domain.Ticket;
import com.project.gym.dto.TicketDto;
import com.project.gym.feign.client.TrainerServiceClient;
import com.project.gym.feign.client.UserServiceClient;
import com.project.gym.feign.dto.LessonResponse;
import com.project.gym.feign.dto.OrderRequest;
import com.project.gym.repository.AttendanceRepository;
import com.project.gym.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GymService {
    private final TicketRepository ticketRepository;
    private final AttendanceRepository attendanceRepository;

    private final TrainerServiceClient trainerServiceClient;

    private final UserServiceClient userServiceClient;

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
        userServiceClient.updateUserType(userId, saveTicket.getType());
        return ticketRepository.save(saveTicket);
    }
}
