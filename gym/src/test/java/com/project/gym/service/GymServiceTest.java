package com.project.gym.service;

import com.project.gym.domain.PersonalUser;
import com.project.gym.domain.Ticket;
import com.project.gym.domain.UserType;
import com.project.gym.dto.TicketDto;
import com.project.gym.feign.dto.LessonResponse;
import com.project.gym.feign.dto.OrderRequest;
import com.project.gym.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GymServiceTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void saveTicketTest(){
        Long orderId = 1L;
        String userId = "user12";
        String paymentType = "무통장입금";
        Long lessonId = 3L;
        String lessonName = "lessonTest";
        Long count = 5L;

        OrderRequest orderRequest = OrderRequest.builder()
                .userId(userId)
                .id(orderId)
                .paymentType(paymentType)
                .build();
        LessonResponse lessonResponse = LessonResponse.builder()
                .id(lessonId)
                .lessonName(lessonName)
                .count(count)
                .build();


        TicketDto personalDto = TicketDto.personalTicket(orderRequest, lessonResponse);
        Ticket ticket = ticketRepository.save(Ticket.personalTicket(personalDto));

        assertEquals(ticket.getUserId(), userId);
        assertEquals(ticket.getLessonId(), lessonId);
        assertEquals(ticket.getType(), UserType.PERSONAL.toString());
    }
}
