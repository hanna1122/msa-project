package com.project.payment.feign.client;

import com.project.payment.feign.dto.LessonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("TRAINER-SERVICE")
public interface TrainerServiceClient {
    @GetMapping("/trainer-service/lesson/{lessonId}")
    LessonResponse getLesson(@PathVariable("lessonId") Long lessonId);
}
