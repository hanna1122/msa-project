package com.project.trainer.domain;

import com.project.trainer.dto.PerformanceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PERFORMANCE")
@EntityListeners(AuditingEntityListener.class)
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long trainerId;
    private Long amount;
    private Long lessonCount;

    public Performance(PerformanceDto performanceDto){
        this.trainerId = performanceDto.getTrainerId();
        this.amount = performanceDto.getAmount();
        this.lessonCount = performanceDto.getLessonCount();
    }
}
