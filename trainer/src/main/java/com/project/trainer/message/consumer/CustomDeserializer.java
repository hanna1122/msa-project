package com.project.trainer.message.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.trainer.message.event.PerformanceSaveEvent;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class CustomDeserializer implements Deserializer<PerformanceSaveEvent> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey){
    }

    @Override
    public PerformanceSaveEvent deserialize(String topic, byte[] data){
        try {
            if(data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), PerformanceSaveEvent.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to PerformanceSaveEvent");
        }
    }

    @Override
    public void close() {
    }
}
