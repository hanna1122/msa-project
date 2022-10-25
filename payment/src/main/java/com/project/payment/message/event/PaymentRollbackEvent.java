package com.project.payment.message.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRollbackEvent {
    private Long paymentId;

    private Long orderId;
}
