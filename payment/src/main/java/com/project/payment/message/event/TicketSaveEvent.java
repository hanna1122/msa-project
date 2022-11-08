package com.project.payment.message.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketSaveEvent {
    private Long id;

    private Long paymentId;

    private String userId;

    private Long lessonId;

    private String paymentType;

    private String lessonName;

    private Long lessonPrice;
}
