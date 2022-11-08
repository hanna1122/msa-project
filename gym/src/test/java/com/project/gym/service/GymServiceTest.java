package com.project.gym.service;

import com.project.gym.domain.Attendance;
import com.project.gym.domain.Ticket;
import com.project.gym.domain.UserType;
import com.project.gym.dto.AttendanceDto;
import com.project.gym.dto.TicketDto;
import com.project.gym.feign.dto.LessonResponse;
import com.project.gym.feign.dto.OrderRequest;
import com.project.gym.message.event.TicketSaveEvent;
import com.project.gym.repository.AttendanceRepository;
import com.project.gym.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GymServiceTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Test
    public void saveTicketTest(){
        Long orderId = 1L;
        String userId = "user12";
        String paymentType = "무통장입금";
        Long lessonId = 3L;
        String lessonName = "lessonTest";
        Long count = 5L;

        TicketSaveEvent ticketSaveEvent = TicketSaveEvent.builder()
                .userId(userId)
                .id(orderId)
                .paymentType(paymentType)
                .build();
        LessonResponse lessonResponse = LessonResponse.builder()
                .id(lessonId)
                .lessonName(lessonName)
                .count(count)
                .build();


        TicketDto personalDto = TicketDto.personalTicket(ticketSaveEvent, lessonResponse);
        Ticket ticket = ticketRepository.save(Ticket.personalTicket(personalDto));

        assertEquals(ticket.getUserId(), userId);
        assertEquals(ticket.getLessonId(), lessonId);
        assertEquals(ticket.getType(), UserType.PERSONAL);
        assertEquals(ticket.getPersonalUser().getCount(), count);
    }

    @Test
    public void getTicketTest(){
        Long ticketId = 1L;
        Long orderId = 1L;
        String userId = "user12";
        String paymentType = "무통장입금";
        Long lessonId = 3L;
        String lessonName = "lessonTest";
        Long count = 5L;

        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        assertEquals(ticket.get().getUserId(), userId);
        assertEquals(ticket.get().getLessonId(), lessonId);
        assertEquals(ticket.get().getType(), UserType.PERSONAL);
        assertEquals(ticket.get().getPersonalUser().getCount(), count);
    }

    @Test
    public void getTicketsTest(){
        Long ticketId = 1L;
        Long orderId = 1L;
        String userId = "user12";
        String paymentType = "무통장입금";
        Long lessonId = 3L;
        String lessonName = "lessonTest";
        Long count = 5L;

        List<Ticket> tickets = ticketRepository.findByUserId(userId);
        assertThat(tickets).contains("user12");
    }

    @Test
    public void saveAttendanceTest(){
        LocalDate today = now();

        String userId = "user12";

        AttendanceDto attendance = AttendanceDto.builder()
                .userId(userId)
                .build();

        Attendance savedAttendance = attendanceRepository.save(new Attendance(attendance));

        assertEquals(savedAttendance.getUserId(), userId);
    }

}
