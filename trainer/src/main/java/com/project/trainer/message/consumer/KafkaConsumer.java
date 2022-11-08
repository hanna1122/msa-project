package com.project.trainer.message.consumer;


import com.project.trainer.Service.TrainerService;
import com.project.trainer.domain.Performance;
import com.project.trainer.domain.Trainers;
import com.project.trainer.message.event.PerformanceSaveEvent;
import com.project.trainer.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumer {

    public final PerformanceRepository performanceRepository;

    public final TrainerService trainerService;

    @KafkaListener(topics = "performance-save-topic", groupId = "myGroup", containerFactory = "performanceSaveListener")
    public void consume(PerformanceSaveEvent event) throws IOException {
        log.info("performance save 이벤트 수신");
        log.info("[trainerId : {}] is saved.", event.getTrainerId());

        Trainers trainer = trainerService.getTrainer(event.getTrainerId());
        performanceRepository.save(new Performance(event, trainer));
    }
}