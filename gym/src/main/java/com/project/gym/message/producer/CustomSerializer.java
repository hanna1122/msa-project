package com.project.gym.message.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gym.message.event.PaymentRollbackEvent;
import com.project.gym.message.event.UserTypeUpdatedEvent;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class CustomSerializer<T> implements Serializer<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey){
    }

    @Override
    public byte[] serialize(String topic, T data) {
        try {
            if(data == null){
                System.out.println("Null received at serializing");
                return null;
            }
            System.out.println("Serializing...");
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing data to byte[]");
        }
    }

    @Override
    public void close() {
    }
}
