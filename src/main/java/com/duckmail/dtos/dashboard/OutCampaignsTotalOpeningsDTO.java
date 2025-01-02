package com.duckmail.dtos.dashboard;

import com.duckmail.dtos.campaign.OutCampaignDTO;

public record OutCampaignsTotalOpeningsDTO(OutCampaignDTO campaign, Integer totalOpenings) {
}