package com.duckmail.infra.rabbitmq;

import com.duckmail.dtos.email.QueuedEmailDTO;
import com.duckmail.enums.CampaignStatus;
import com.duckmail.models.CampaignEmailTemplate;
import com.duckmail.services.CampaignEmailTemplateService;
import com.duckmail.services.CampaignService;
import com.duckmail.services.DeliveryErrorLogService;
import com.duckmail.services.impl.EmailSenderLambdaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitEmailListener {
    private final ConnectionFactory connectionFactory;
    private final ObjectMapper objectMapper;
    private final EmailSenderLambdaService emailSenderLambdaService;
    private final DeliveryErrorLogService deliveryErrorLogService;
    private final CampaignService campaignService;
    private final CampaignEmailTemplateService campaignEmailTemplateService;
    private final RabbitQueueInspector rabbitQueueInspector;
    private String queueName;
    private CampaignEmailTemplate campaignEmailTemplate;
    private Integer messageCount;

    public void registerListener(CampaignEmailTemplate campaignEmailTemplate) {
        this.campaignEmailTemplate = campaignEmailTemplate;
        this.queueName = "campaign-email-template-" + campaignEmailTemplate.getId();
        this.messageCount = rabbitQueueInspector.getMessageCount(queueName);

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setMessageListener(this::consumeMessage);

        container.start();
    }

    private void consumeMessage(Message message) {
        try {
            var queuedEmail = objectMapper.readValue(message.getBody(), QueuedEmailDTO.class);
            emailSenderLambdaService.invokeLambda(queuedEmail);

            this.messageCount--;

            if (this.messageCount < 1) {
                campaignService.updateCampaignStatus(campaignEmailTemplate.getCampaign().getId(), CampaignStatus.COMPLETED);

                rabbitQueueInspector.deleteQueue(queueName);
            }
        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
            throw new RuntimeException("Erro ao consumir mensagem", e);
        }
    }
}