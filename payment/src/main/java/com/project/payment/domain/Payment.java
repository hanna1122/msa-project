package com.project.payment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.payment.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Table(name = "PAYMENT")
@EntityListeners(AuditingEntityListener.class)
public class Payment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private String userId;

    private Long lessonId;

    private Long lessonPrice;

    private String paymentType;


    public Payment(OrderDto orderDto){
        this.orderId = orderDto.getId();
        this.userId = orderDto.getUserId();
        this.lessonId = orderDto.getLessonId();
        this.lessonPrice = orderDto.getLessonPrice();
        this.paymentType = orderDto.getPaymentType();
    }

}
