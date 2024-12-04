package com.duckmail.infra.email;

import com.duckmail.infra.rabbitmq.EmailQueueListenerService;
import com.duckmail.infra.rabbitmq.RabbitEmailConsumer;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class RegisterEmailQueueListenerJob implements Job {
    private final RabbitEmailConsumer rabbitEmailConsumer;
    private final EmailQueueListenerService emailQueueListenerService;

    public RegisterEmailQueueListenerJob(RabbitEmailConsumer rabbitEmailConsumer, EmailQueueListenerService emailQueueListenerService) {
        this.rabbitEmailConsumer = rabbitEmailConsumer;
        this.emailQueueListenerService = emailQueueListenerService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        Long campaignEmailTemplateId = jobDataMap.getLong("campaignEmailTemplateId");

        emailQueueListenerService.registerListener(campaignEmailTemplateId, rabbitEmailConsumer);
    }
}