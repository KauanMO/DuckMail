package com.duckmail.infra.rabbitmq;

import com.duckmail.dtos.email.QueuedEmailDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class RabbitEmailConsumer {
    private final ObjectMapper objectMapper;

    public RabbitEmailConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void consumeMessage(String message) {
        try {
            var queuedEmail = objectMapper.readValue(message, QueuedEmailDTO.class);

            System.out.println(queuedEmail);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
