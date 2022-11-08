package com.project.trainer.message.event;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceSaveEvent {
    private Long trainerId;
    private Long amount;
    private Long lessonCount;
}
