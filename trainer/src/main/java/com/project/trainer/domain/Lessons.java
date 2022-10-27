package com.project.trainer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.trainer.dto.LessonDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "LESSONS")
@EntityListeners(AuditingEntityListener.class)
public class Lessons extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trainerId;

    @Column(length = 20, nullable = false)
    private String lessonName;

    private Long price;

    private Long count;

    @Enumerated(EnumType.STRING)
    private LessonType lessonType;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate endDate;


    public Lessons(LessonDto lessonDto){
        this.trainerId = lessonDto.getTrainerId();
        this.lessonName = lessonDto.getLessonName();
        this.price = lessonDto.getPrice();
        this.count = lessonDto.getCount();
        this.lessonType = lessonDto.getLessonType();
        this.startDate = lessonDto.getStartDate();
        this.endDate = lessonDto.getEndDate();
    }

}
