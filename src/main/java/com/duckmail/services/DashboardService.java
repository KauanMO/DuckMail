package com.duckmail.services;

import com.duckmail.dtos.dashboard.OutCampaignsTotalOpeningsDTO;

import java.util.List;

public interface DashboardService {
    Long getTotalDeliveryErrors();

    Long getTotalClickHistories();

    Long getTotalOpenHistories();

    Long getTotalPendingCampaigns();

    List<OutCampaignsTotalOpeningsDTO> getCampaignsTotalOpenings();
}