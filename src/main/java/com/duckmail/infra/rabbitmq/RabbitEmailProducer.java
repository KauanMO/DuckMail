package com.duckmail.infra.rabbitmq;

import com.duckmail.dtos.email.QueuedEmailDTO;
import com.duckmail.models.Recipient;
import com.duckmail.services.impl.EmailBodyBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitEmailProducer {
    private final RabbitAdmin rabbitAdmin;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitEmailProducer(RabbitAdmin rabbitAdmin, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitAdmin = rabbitAdmin;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void generateEmailQueue(Long campaignEmailTemplateId) {
        Queue queue = new Queue("campaign-email-template-" + campaignEmailTemplateId);
        rabbitAdmin.declareQueue(queue);
    }

    public void enqueueRecipient(Recipient recipient) {
        String queueName = "campaign-email-template-" + recipient.getCampaignEmailTemplate().getId();

        var campaignEmailTemplate = recipient.getCampaignEmailTemplate();
        var emailTemplate = recipient.getCampaignEmailTemplate().getEmailTemplate();

        EmailBodyBuilder emailBodyBuilder = new EmailBodyBuilder(emailTemplate);

        String emailBody = emailBodyBuilder
                .alocateUrl(campaignEmailTemplate.getUrl())
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
            throw new RuntimeException(e);
        }

    }
}
