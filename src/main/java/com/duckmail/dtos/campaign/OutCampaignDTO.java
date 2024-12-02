package com.duckmail.dtos.campaign;

import java.time.LocalDateTime;

import com.duckmail.enums.CampaignStatus;
import com.duckmail.models.Campaign;

public record OutCampaignDTO(
        Long id,
        String name,
        String description,
        CampaignStatus status,
        LocalDateTime createdDate,
        LocalDateTime scheduledDate,
        int totalCount) {
    public OutCampaignDTO(Campaign c) {
        this(c.getId(), c.getName(), c.getDescription(),
                c.getStatus(), c.getCreatedDate(), c.getScheduledDate(),
                c.getTotalCount());
    }
}
