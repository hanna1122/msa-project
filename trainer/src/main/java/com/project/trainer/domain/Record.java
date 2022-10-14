package com.project.trainer.domain;

import com.project.trainer.dto.RecordDto;
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
@Table(name = "Record")
@EntityListeners(AuditingEntityListener.class)
public class Record extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private Long lessonId;

    private String content;

    private Long time;

    public Record(RecordDto recordDto, String trainerId){
        this.userId = recordDto.getUserId();
        this.lessonId = recordDto.getLessonId();
        this.content = recordDto.getContent();
        this.time = recordDto.getTime();
    }
}
