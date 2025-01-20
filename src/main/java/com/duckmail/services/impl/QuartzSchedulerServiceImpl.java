package com.duckmail.services.impl;

import com.duckmail.infra.email.RegisterEmailQueueListenerJob;
import com.duckmail.models.Campaign;
import com.duckmail.services.QuartzSchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Service
public class QuartzSchedulerServiceImpl implements QuartzSchedulerService {
    private final Scheduler scheduler;

    public QuartzSchedulerServiceImpl(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void scheduleCampaign(Campaign campaign) {
        LocalDateTime campaignScheduledDate = campaign.getScheduledDate();

        JobDetail jobDetail = JobBuilder.newJob(RegisterEmailQueueListenerJob.class)
                .withIdentity("Campaign-job-" + campaign.getId())
                .usingJobData("campaign", campaign.getId())
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("Campaign-trigger-" + campaign.getId())
                .startAt(Date.from(campaignScheduledDate.atZone(ZoneId.of("America/Sao_Paulo")).toInstant()))
                .build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("Scheduler exception: ", e);
        }
    }

    @Override
    public void updateScheduledCampaignDate(Campaign campaign, LocalDateTime newDate) {
        TriggerKey oldTrigger = TriggerKey.triggerKey("Campaign-trigger-" + campaign.getId());
        Trigger newTrigger = TriggerBuilder.newTrigger()
                .withIdentity("Campaign-trigger-" + campaign.getId())
                .startAt(Date.from(newDate.atZone(ZoneId.of("America/Sao_Paulo")).toInstant()))
                .build();

        try {
            scheduler.rescheduleJob(oldTrigger, newTrigger);
        } catch (SchedulerException e) {
            log.error("Scheduler exception: ", e);
        }
    }
}
