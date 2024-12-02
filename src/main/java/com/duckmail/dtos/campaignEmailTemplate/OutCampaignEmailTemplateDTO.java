package com.duckmail.dtos.campaignEmailTemplate;

import java.time.LocalDateTime;

import com.duckmail.models.CampaignEmailTemplate;

public record OutCampaignEmailTemplateDTO(
        Long id,
        String url,
        LocalDateTime createdDate) {
    public OutCampaignEmailTemplateDTO(CampaignEmailTemplate cet) {
        this(cet.getId(),cet.getUrl(), cet.getCreatedDate());
    }
}