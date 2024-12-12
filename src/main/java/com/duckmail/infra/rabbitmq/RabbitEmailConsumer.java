package com.duckmail.infra.rabbitmq;

import com.duckmail.dtos.email.QueuedEmailDTO;
import com.duckmail.infra.email.EmailSenderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class RabbitEmailConsumer {
    private final ObjectMapper objectMapper;
    private final EmailSenderService emailSenderService;

    public RabbitEmailConsumer(ObjectMapper objectMapper, EmailSenderService emailSenderService) {
        this.objectMapper = objectMapper;
        this.emailSenderService = emailSenderService;
    }

    public void consumeMessage(String message) {
        try {
            var queuedEmail = objectMapper.readValue(message, QueuedEmailDTO.class);

            emailSenderService.sendQueuedEmail(queuedEmail);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
