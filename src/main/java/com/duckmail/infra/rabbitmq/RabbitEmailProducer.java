package com.duckmail.infra.rabbitmq;

import com.duckmail.dtos.email.QueuedEmailDTO;
import com.duckmail.models.Recipient;
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

        QueuedEmailDTO queuedEmail = new QueuedEmailDTO(recipient);

        try {
            String message = objectMapper.writeValueAsString(queuedEmail);

            rabbitTemplate.convertAndSend(queueName, message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
