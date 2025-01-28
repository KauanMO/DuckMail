package com.duckmail.services;

import com.duckmail.dtos.campaign.InCampaignDTO;
import com.duckmail.enums.CampaignStatus;
import com.duckmail.models.Campaign;
import org.quartz.SchedulerException;

import java.time.LocalDateTime;
import java.util.List;

public interface CampaignService extends Writable<Campaign, InCampaignDTO>,
        Readable<Campaign, Long> {
    List<Campaign> getPendingCampaigns();

    Campaign updateCampaignStatus(Long campaignId, CampaignStatus newStatus);

    List<Campaign> getAllCampaigns();

    Campaign updateCampaignScheduledDate(Long campaignId, LocalDateTime newDateTime) throws SchedulerException;
}