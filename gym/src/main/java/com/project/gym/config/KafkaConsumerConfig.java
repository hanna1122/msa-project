package com.project.gym.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gym.message.event.TicketSaveEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;
    private final ObjectMapper objectMapper;


    @Bean
    public Map<String, Object> consumerConfiguration() {
        return new HashMap<>(kafkaProperties.buildConsumerProperties());
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfiguration());
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TicketSaveEvent> ticketSaveListener() {
        ConcurrentKafkaListenerContainerFactory<String, TicketSaveEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
