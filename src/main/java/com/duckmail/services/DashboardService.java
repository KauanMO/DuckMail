package com.duckmail.services;

import com.duckmail.dtos.dashboard.DailyHourSegregationEnum;
import com.duckmail.dtos.dashboard.OutCampaignsTotalOpeningsDTO;
import com.duckmail.models.Campaign;

import java.util.List;
import java.util.Map;

public interface DashboardService {
    Long getTotalDeliveryErrors();

    Long getTotalClickHistories();

    Long getTotalOpenHistories();

    Long getTotalPendingCampaigns();

    List<OutCampaignsTotalOpeningsDTO> getCampaignsTotalOpenings();

    Map<String, Map<String, Integer>> getTotalOpeningsByHour();
}