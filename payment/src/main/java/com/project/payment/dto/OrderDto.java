package com.project.payment.dto;

import com.project.payment.domain.Order;
import com.project.payment.domain.OrderStatus;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDto {
    private Long id;

    @Setter
    private Long paymentId;

    private String userId;

    private Long trainerId;

    private Long lessonId;

    private String paymentType;

    private String lessonName;

    private Long lessonPrice;


    public static OrderDto of(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.id = order.getId();
        orderDto.userId = order.getUserId();
        orderDto.trainerId = orderDto.getTrainerId();
        orderDto.lessonId = order.getLessonId();
        orderDto.lessonName = order.getLessonName();
        orderDto.lessonPrice = order.getLessonPrice();
        orderDto.paymentType = order.getPaymentType();

        return orderDto;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isSame = false;
        if(obj != null && obj instanceof OrderDto) {
            if(this.userId == ((OrderDto) obj).getUserId()
                    && this.lessonId == ((OrderDto) obj).getLessonId()
                    && this.lessonPrice == ((OrderDto) obj).getLessonPrice()
                    && this.lessonName == ((OrderDto) obj).getLessonName()){
                isSame = true;
            }
        }
        return isSame;
    }
}
