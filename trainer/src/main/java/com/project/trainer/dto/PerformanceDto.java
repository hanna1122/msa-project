package com.project.trainer.dto;

import com.project.trainer.domain.Performance;
import com.project.trainer.domain.Trainers;
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
                .trainerId(performance.getTrainerId().getId())
                .amount(performance.getAmount())
                .lessonCount(performance.getLessonCount())
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        boolean isSame = false;
        if(obj != null && obj instanceof PerformanceDto) {
            if(this.trainerId == ((PerformanceDto) obj).getTrainerId()
                    && this.amount == ((PerformanceDto) obj).getAmount()
                    && this.lessonCount == ((PerformanceDto) obj).getLessonCount()){
                isSame = true;
            }
        }
        return isSame;
    }
}
