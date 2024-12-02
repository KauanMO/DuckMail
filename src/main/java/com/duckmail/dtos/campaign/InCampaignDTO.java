package com.duckmail.dtos.campaign;

import java.time.LocalDateTime;

import com.duckmail.enums.CampaignStatus;

public record InCampaignDTO(
        String name,
        String description,
        CampaignStatus status,
        LocalDateTime scheduledDate) {

}