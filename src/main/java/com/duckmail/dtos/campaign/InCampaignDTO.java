package com.duckmail.dtos.campaign;

import java.time.LocalDateTime;

public record InCampaignDTO(
        String name,
        String description,
        LocalDateTime scheduledDate) {

}