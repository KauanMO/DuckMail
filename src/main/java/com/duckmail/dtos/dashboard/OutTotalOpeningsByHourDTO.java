package com.duckmail.dtos.dashboard;

import com.duckmail.models.Campaign;

import java.util.Map;

public record OutTotalOpeningsByHourDTO(
    DailyHourSegregationEnum hourSegregation,
    Map<Campaign, Integer> totalOpeningsPerCampaign
) {
}