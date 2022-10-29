package com.project.trainer.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecordDto {

    private String userId;

    private Long trainerId;

    private Long lessonId;

    private String content;

    private Long time;
}
