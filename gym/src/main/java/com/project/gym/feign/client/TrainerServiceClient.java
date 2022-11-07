package com.project.gym.feign.client;

import com.project.gym.dto.Result;
import com.project.gym.feign.dto.LessonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("TRAINER-SERVICE")
public interface TrainerServiceClient {
    @GetMapping("/trainer-service/lesson/{lessonId}")
    Result<LessonResponse> getLesson(@PathVariable("lessonId") Long lessonId);
}
