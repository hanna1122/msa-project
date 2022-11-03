package com.project.reservation.message.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.reservation.message.event.ReservationRollbackEvent;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class CustomDeserializer implements Deserializer<ReservationRollbackEvent> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey){
    }

    @Override
    public ReservationRollbackEvent deserialize(String topic, byte[] data){
        try {
            if(data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), ReservationRollbackEvent.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to ReservationRollbackEvent");
        }
    }

    @Override
    public void close() {
    }
}
