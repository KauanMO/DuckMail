package com.duckmail.services;

import com.duckmail.dtos.campaign.InCampaignDTO;
import com.duckmail.models.Campaign;

public interface CampaignService extends Writable<Campaign, InCampaignDTO>,
        Readable<Campaign, Long> {

}