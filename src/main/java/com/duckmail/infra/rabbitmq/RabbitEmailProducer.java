package com.duckmail.infra.rabbitmq;

import com.duckmail.dtos.email.QueuedEmailDTO;
import com.duckmail.models.Recipient;
import com.duckmail.services.DeliveryErrorLogService;
import com.duckmail.services.impl.EmailBodyBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class RabbitEmailProducer {
    private final RabbitAdmin rabbitAdmin;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final EmailBodyBuilder emailBodyBuilder;
    private final DeliveryErrorLogService deliveryErrorLogService;

    public RabbitEmailProducer(RabbitAdmin rabbitAdmin, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, EmailBodyBuilder emailBodyBuilder, DeliveryErrorLogService deliveryErrorLogService) {
        this.rabbitAdmin = rabbitAdmin;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.emailBodyBuilder = emailBodyBuilder;
        this.deliveryErrorLogService = deliveryErrorLogService;
    }

    public void generateEmailQueue(Long campaignEmailTemplateId) {
        Queue queue = new Queue("campaign-email-template-" + campaignEmailTemplateId);
        rabbitAdmin.declareQueue(queue);
    }

    public void enqueueRecipient(Recipient recipient) {
        String queueName = "campaign-email-template-" + recipient.getCampaignEmailTemplate().getId();

        var emailTemplate = recipient.getCampaignEmailTemplate().getEmailTemplate();

        String emailBody = emailBodyBuilder
                .setBody(emailTemplate.getHtmlBody() != null ? emailTemplate.getHtmlBody() : emailTemplate.getTextBody())
                .alocateUrl(recipient)
                .applyWatermark(recipient)
                .build();

        QueuedEmailDTO queuedEmail = new QueuedEmailDTO(emailTemplate.getSubject(),
                emailBody,
                recipient.getEmail(),
                recipient.getId());

        try {
            String message = objectMapper.writeValueAsString(queuedEmail);

            rabbitTemplate.convertAndSend(queueName, message);
        } catch (JsonProcessingException e) {
            deliveryErrorLogService.registerError(e.getMessage(), LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime(), recipient.getId());
        }
    }
}
