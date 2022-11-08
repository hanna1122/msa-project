package com.project.gym.message.consumer;

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
public class KafkaTicketConsumer {

    private final GymService gymService;

    @KafkaListener(topics = "ticket-save-topic", groupId = "myGroup", containerFactory = "ticketSaveListener")
    public void consume(TicketSaveEvent event) throws IOException {
        log.info("ticketSave 이벤트 수신");
        log.info("[Id : {}, paymentId : {}] is saved.", event.getId(), event.getPaymentId());
        gymService.saveTicket(event);

    }
}