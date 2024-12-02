package com.duckmail.dtos.campaignEmailTemplate;

public record InCampaignEmailTemplate(
        String url,
        Long campaignId,
        Long emailTemplateId) {

}