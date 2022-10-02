package com.project.payment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.payment.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Table(name = "ORDERS")
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private Long lessonId;

    private String lessonName;

    private Long lessonPrice;

    private String paymentType;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime regDate;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime modDate;

    public void setOrderStatus(OrderStatus orderStatus){
        this.orderStatus = orderStatus;
    }

    public Order(OrderDto orderDto){
        this.userId = orderDto.getUserId();
        this.lessonId = orderDto.getLessonId();
        this.lessonName = orderDto.getLessonName();
        this.lessonPrice = orderDto.getLessonPrice();
        this.paymentType = orderDto.getPaymentType();
        this.orderStatus = OrderStatus.PLACED;
    }

}
