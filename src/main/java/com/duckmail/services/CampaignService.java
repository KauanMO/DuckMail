package com.duckmail.services;

import com.duckmail.dtos.campaign.InCampaignDTO;
import com.duckmail.models.Campaign;

import java.util.List;

public interface CampaignService extends Writable<Campaign, InCampaignDTO>,
        Readable<Campaign, Long> {
    List<Campaign> getPendingCampaigns();
}