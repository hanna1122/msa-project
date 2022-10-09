package com.project.trainer.dto;

import com.project.trainer.domain.Performance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceDto {
    private Long trainerId;
    private Long amount;
    private Long lessonCount;

    public static PerformanceDto of(Performance performance){
        return PerformanceDto.builder()
                .trainerId(performance.getTrainerId())
                .amount(performance.getAmount())
                .lessonCount(performance.getLessonCount())
                .build();
    }
}
