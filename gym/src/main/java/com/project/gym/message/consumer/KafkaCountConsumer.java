package com.project.gym.message.consumer;

import com.project.gym.message.event.CountUpdatedEvent;
import com.project.gym.message.event.TicketSaveEvent;
import com.project.gym.service.GymService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaCountConsumer {

    private final GymService gymService;

    @KafkaListener(topics = "count-updated-topic", groupId = "myGroup", containerFactory = "countUpdatedListener")
    public void consume(CountUpdatedEvent event) throws IOException {
        log.info("count-updated 이벤트 수신");
        log.info("[ticketId : {}, reservationId : {}, reservationStatus : {} is updated.", event.getId(), event.getReservationId(), event.getReservationStatus());
        gymService.updateCount(event.getId(), event.getReservationId(), event.getReservationStatus());

    }
}