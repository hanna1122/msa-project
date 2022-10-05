package com.project.gym.feign.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderRequest {
    private Long id;

    private String userId;

    private Long lessonId;

    private String paymentType;

    private String lessonName;

    private Long lessonPrice;
}
