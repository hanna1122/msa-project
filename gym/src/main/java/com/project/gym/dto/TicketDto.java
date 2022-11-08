package com.project.gym.dto;

import com.project.gym.domain.Ticket;
import com.project.gym.domain.UserType;
import com.project.gym.feign.dto.LessonResponse;
import com.project.gym.feign.dto.OrderRequest;
import com.project.gym.message.event.TicketSaveEvent;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TicketDto {

    private Long id;

    private String userId;

    private Long orderId;

    private Long lessonId;

    private Long count;

    private String useYn;

    private UserType type;

    private LocalDate startDate;

    private LocalDate endDate;


    public static TicketDto generalTicket(TicketSaveEvent orderRequest, LessonResponse lessonResponse){
        return TicketDto.builder()
                .userId(orderRequest.getUserId())
                .orderId(orderRequest.getId())
                .lessonId(lessonResponse.getId())
                .startDate(lessonResponse.getStartDate())
                .endDate(lessonResponse.getEndDate())
                .type(UserType.GENERAL)
                .build();
    }

    public static TicketDto personalTicket(TicketSaveEvent orderRequest, LessonResponse lessonResponse){
        return TicketDto.builder()
                .userId(orderRequest.getUserId())
                .orderId(orderRequest.getId())
                .lessonId(lessonResponse.getId())
                .count(lessonResponse.getCount())
                .type(UserType.PERSONAL)
                .build();
    }

    public static TicketDto of(Ticket ticket){
        return TicketDto.builder()
                .id(ticket.getId())
                .userId(ticket.getUserId())
                .lessonId(ticket.getLessonId())
                .count(ticket.getPersonalUser().getCount())
                .useYn(ticket.getUseYn())
                .build();
    }

    public void setCount(Long count){
        this.count = count;
    }
}
