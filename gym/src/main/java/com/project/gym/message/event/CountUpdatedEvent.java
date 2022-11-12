package com.project.gym.message.event;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountUpdatedEvent {
    private Long id;
    private Long reservationId;
    private String reservationStatus;
}
