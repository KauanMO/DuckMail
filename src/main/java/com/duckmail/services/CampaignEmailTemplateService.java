package com.duckmail.services;

import com.duckmail.dtos.campaignEmailTemplate.InCampaignEmailTemplateDTO;
import com.duckmail.models.CampaignEmailTemplate;

public interface CampaignEmailTemplateService extends Writable<CampaignEmailTemplate, InCampaignEmailTemplateDTO>,
        Readable<CampaignEmailTemplate, Long> {

}