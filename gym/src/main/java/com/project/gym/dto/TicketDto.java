package com.project.gym.dto;

import com.project.gym.feign.dto.LessonResponse;
import com.project.gym.feign.dto.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {

    private String userId;

    private Long orderId;

    private Long lessonId;

    private Long count;


    private LocalDate startDate;

    private LocalDate endDate;


    public static TicketDto generalTicket(OrderRequest orderRequest, LessonResponse lessonResponse){
        return TicketDto.builder()
                .userId(orderRequest.getUserId())
                .orderId(orderRequest.getId())
                .lessonId(lessonResponse.getId())
                .startDate(lessonResponse.getStartDate())
                .endDate(lessonResponse.getEndDate())
                .build();
    }

    public static TicketDto personalTicket(OrderRequest orderRequest, LessonResponse lessonResponse){
        return TicketDto.builder()
                .userId(orderRequest.getUserId())
                .orderId(orderRequest.getId())
                .lessonId(lessonResponse.getId())
                .count(lessonResponse.getCount())
                .build();
    }
}
