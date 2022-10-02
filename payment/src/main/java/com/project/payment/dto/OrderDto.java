package com.project.payment.dto;

import com.project.payment.domain.Order;
import com.project.payment.domain.OrderStatus;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDto {
    private String userId;

    private Long lessonId;

    private String paymentType;

    private String lessonName;

    private Long lessonPrice;


    public static OrderDto of(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.userId = order.getUserId();
        orderDto.lessonId = order.getLessonId();
        orderDto.lessonName = order.getLessonName();
        orderDto.lessonPrice = order.getLessonPrice();
        orderDto.paymentType = order.getPaymentType();

        return orderDto;
    }
}
