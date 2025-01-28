package com.duckmail.infra.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

@Service
public class RabbitQueueInspector {
    private final RabbitAdmin rabbitAdmin;

    public RabbitQueueInspector(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    public Integer getMessageCount(String queueName) {
        return rabbitAdmin.getQueueInfo(queueName).getMessageCount();
    }

    public void deleteQueue(String queueName) {
        rabbitAdmin.deleteQueue(queueName);
    }
}