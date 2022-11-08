package com.project.gym.message.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gym.message.event.TicketSaveEvent;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class CustomDeserializer implements Deserializer<TicketSaveEvent> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey){
    }

    @Override
    public TicketSaveEvent deserialize(String topic, byte[] data){
        try {
            if(data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), TicketSaveEvent.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SerializationException("Error when deserializing byte[] to TicketSaveEvent");
        }
    }

    @Override
    public void close() {
    }
}
