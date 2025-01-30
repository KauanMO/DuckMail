package com.duckmail.infra.email;

import com.duckmail.infra.rabbitmq.RabbitEmailListener;
import com.duckmail.models.Campaign;
import com.duckmail.models.CampaignEmailTemplate;
import com.duckmail.services.CampaignService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterEmailQueueListenerJob implements Job {
    private final RabbitEmailListener rabbitEmailListener;
    private final CampaignService campaignService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        Long campaignId = jobDataMap.getLong("campaign");

        Campaign campaignFound = campaignService.findById(campaignId);

        for (CampaignEmailTemplate campaignEmailTemplate : campaignFound.getCampaignEmailTemplates())
            rabbitEmailListener.registerListener(campaignEmailTemplate);
    }
}