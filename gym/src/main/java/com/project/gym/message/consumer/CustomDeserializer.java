package com.project.gym.message.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gym.message.event.CountUpdatedEvent;
import com.project.gym.message.event.TicketSaveEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CustomDeserializer<T> implements Deserializer<T> {
    private final ObjectMapper objectMapper;

    private final Map<String, Class<?>> maps;

    public CustomDeserializer() {
        this.objectMapper = new ObjectMapper();
        maps = new HashMap<>(2);
        maps.put("ticket-save-topic", TicketSaveEvent.class);
        maps.put("count-updated-topic", CountUpdatedEvent.class);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey){
    }

    @Override
    public T deserialize(String topic, byte[] data){
        try {
            if(data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return (T) objectMapper.readValue(new String(data, "UTF-8"), maps.get(topic));
        } catch (Exception e) {
            e.printStackTrace();
            throw new SerializationException("Error when deserializing byte[] to Object.");
        }
    }

    @Override
    public void close() {
    }
}
