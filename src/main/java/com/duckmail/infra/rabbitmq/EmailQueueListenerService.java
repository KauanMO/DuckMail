package com.duckmail.infra.rabbitmq;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Service;

@Service
public class EmailQueueListenerService {
    private final ConnectionFactory connectionFactory;

    public EmailQueueListenerService(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void registerListener(Long campaignEmailTemplateId, Object listener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("campaign-email-template-" + campaignEmailTemplateId);
        container.setMessageListener(new MessageListenerAdapter(listener, "consumeMessage"));

        container.start();
    }
}
