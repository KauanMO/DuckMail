package com.duckmail.infra.email;

import com.duckmail.infra.rabbitmq.RabbitEmailListener;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class RegisterEmailQueueListenerJob implements Job {
    private final RabbitEmailListener rabbitEmailListener;

    public RegisterEmailQueueListenerJob(RabbitEmailListener rabbitEmailListener) {
        this.rabbitEmailListener = rabbitEmailListener;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        Long campaignEmailTemplateId = jobDataMap.getLong("campaignEmailTemplateId");

        rabbitEmailListener.registerListener(campaignEmailTemplateId);
    }
}