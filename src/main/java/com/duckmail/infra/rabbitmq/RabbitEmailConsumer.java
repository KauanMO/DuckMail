package com.duckmail.infra.rabbitmq;

import com.duckmail.dtos.email.QueuedEmailDTO;
import com.duckmail.infra.email.EmailSenderService;
import com.duckmail.services.DeliveryErrorLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class RabbitEmailConsumer {
    private final ObjectMapper objectMapper;
    private final EmailSenderService emailSenderService;
    private final DeliveryErrorLogService deliveryErrorLogService;

    public RabbitEmailConsumer(ObjectMapper objectMapper, EmailSenderService emailSenderService, DeliveryErrorLogService deliveryErrorLogService) {
        this.objectMapper = objectMapper;
        this.emailSenderService = emailSenderService;
        this.deliveryErrorLogService = deliveryErrorLogService;
    }

    public void consumeMessage(String message) {
        try {
            var queuedEmail = objectMapper.readValue(message, QueuedEmailDTO.class);

            emailSenderService.sendQueuedEmail(queuedEmail);
        } catch (JsonProcessingException e) {
            deliveryErrorLogService.registerError(e.getMessage(), LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime());
        }
    }
}
