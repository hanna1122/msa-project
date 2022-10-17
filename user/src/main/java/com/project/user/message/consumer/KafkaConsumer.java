package com.project.user.message.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class KafkaConsumer {

    @KafkaListener(topics = "exam", groupId = "myGroup")
    public void consume(String message) throws IOException {
        System.out.println(String.format("Consumer message : %s", message));
    }
}