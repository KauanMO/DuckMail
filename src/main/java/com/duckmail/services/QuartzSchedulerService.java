package com.duckmail.services;

import com.duckmail.models.Campaign;
import org.quartz.SchedulerException;

import java.time.LocalDateTime;

public interface QuartzSchedulerService {
    void scheduleCampaign(Campaign campaign);
    void updateScheduledCampaignDate(Campaign campaign, LocalDateTime newDate);
}